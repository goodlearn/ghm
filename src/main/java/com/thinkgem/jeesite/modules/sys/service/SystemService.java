package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.assist.dao.AssistDao;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.userinfo.dao.UserinfoDao;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
	@Autowired
	private UserinfoDao userinfoDao;
	
	@Autowired
	private AssistDao assistDao;
	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	
	/**
	 * 街道社区对应值
	 */
	private static Map<String, List<String>> street_community = Maps.newHashMap();
	/**
	 * 街道社区对应状态
	 */
	private static Map<Integer, List<Dict>> assist_state = Maps.newHashMap();
	
	/**
	 * 查询用户权限
	 * 只有四种权限 无权限 工会 街道 社区
	 */
	public int findRoleValue(User user) {
		if(user.isAdmin()) {
			return Global.GH_VALUE;
		}
		List<Role> roleList = user.getRoleList();
		if(null == roleList || roleList.size() == 0) {
			return Global.NULL_VALUE;
		}
		
		int ret = 0;
		for(Role forRole : roleList) {
			String id = forRole.getId();
			int temp = findRoleValueById(id);
			if(temp > ret) {
				ret = temp;
			}
		}
		return ret;
	}
	
	//街道对应的社区号
	public List<String> findCkBySk(String communityKey){
		if(street_community.isEmpty()) {
			addDateSc();
		}
		return street_community.get(communityKey);
	}
	
	//获取对应的状态
	public List<Dict> findDict(int key){
		if(assist_state.isEmpty()) {
			addDateAssistState();
		}
		return assist_state.get(key);
	}
	
	//填充街道社区对应数据
	private void addDateAssistState() {
		
		List<Dict> dictList = DictUtils.getDictList("assistState");
		//社区
		List<Dict> community = new ArrayList<Dict>();
		for(Dict forDict :dictList) {
			String value = forDict.getValue();
			if(value.equals("0")||
					value.equals("1")) {
				community.add(forDict);
			}
		}
		Integer communityKey = Global.COMMUNITY_VALUE;
		assist_state.put(communityKey, community);
		
		//街道
		List<Dict> street = new ArrayList<Dict>();
		for(Dict forDict :dictList) {
			String value = forDict.getValue();
			if(value.equals("1")||
					value.equals("2")) {
				street.add(forDict);
			}
		}
		Integer streetKey = Global.STREET_VALUE;
		assist_state.put(streetKey, street);
		
		//工会
		List<Dict> gh = new ArrayList<Dict>();
		for(Dict forDict :dictList) {
			String value = forDict.getValue();
			if(value.equals("2")||
					value.equals("3")||
						value.equals("4")) {
				gh.add(forDict);
			}
		}
		Integer ghKey = Global.GH_VALUE;
		assist_state.put(ghKey, street);
		
	}
	
	//填充街道社区对应数据
	private void addDateSc() {
		
		//杭盖街道办事处
		List<String> hgValue = new ArrayList<String>();
		String hgKey = "43";
		hgValue.add("9");//青格勒社区
		hgValue.add("14");//东风社区
		hgValue.add("12");//呼格吉勒社区
		hgValue.add("13");//长安社区
		hgValue.add("15");//东胜社区
		hgValue.add("16");//巴达日呼社区
		hgValue.add("10");//德尔斯图社区
		street_community.put(hgKey, hgValue);
		
		//楚古兰街道办事处
		List<String> cglValue = new ArrayList<String>();
		String cglKey = "44";
		cglValue.add("2");//德吉社区
		cglValue.add("0");//扎斯嘎图社区
		cglValue.add("4");//伊特格勒社区
		cglValue.add("1");//乌兰牧骑社区
		cglValue.add("7");//爱民社区
		cglValue.add("3");//楚鲁图社区
		cglValue.add("8");//格日勒图社区
		cglValue.add("6");//阿尔山社区
		street_community.put(cglKey,cglValue );
		
		//额尔敦街道办事
		List<String> eedValue = new ArrayList<String>();
		String eedlKey = "45";
		eedValue.add("18");//振兴社区
		eedValue.add("19");//葛根敖包社区
		eedValue.add("23");//新艾里社区
		eedValue.add("20");//桃林塔拉社区
		eedValue.add("21");//阿日噶朗图社区
		eedValue.add("17");//罕尼乌拉社区
		eedValue.add("22");//额尔德木腾社区
		eedValue.add("24");//华油社区
		street_community.put(eedlKey, eedValue);
		
		//希日塔拉街道办事处
		List<String> xrtlValue = new ArrayList<String>();
		String xrtllKey = "46";
		xrtlValue.add("27");//乌兰社区
		xrtlValue.add("28");//察哈尔社区
		xrtlValue.add("29");//巴彦社区
		xrtlValue.add("30");//花园社区
		xrtlValue.add("31");//那达慕社区
		xrtlValue.add("25");//新华社区
		xrtlValue.add("26");//赛罕社区
		street_community.put(xrtllKey, xrtlValue);
		
		//宝力根街道办事处
		List<String> blgjValue = new ArrayList<String>();
		String blgjKey = "47";
		blgjValue.add("33");//额尔敦社区
		blgjValue.add("34");//回民社区
		blgjValue.add("35");//宝力根社区
		blgjValue.add("36");//拉布仁社区
		blgjValue.add("37");//兴盛社区
		blgjValue.add("38");//甘珠尔社区
		blgjValue.add("39");//乌兰图噶社区
		street_community.put(blgjKey, blgjValue);
		
		//南郊街道办事处
		List<String> njValue = new ArrayList<String>();
		String njKey = "42";
		street_community.put(njKey, njValue);
		
		//巴彦查干街道办事处
		List<String> bycgValue = new ArrayList<String>();
		String bycgKey = "48";
		blgjValue.add("40");//达布希勒特社区
		blgjValue.add("39");//塔林社区
		blgjValue.add("41");//锡林社区
		street_community.put(bycgKey, bycgValue);
		
	}
	
	public List<Dict> findAssistState(User user){
		if(user.isAdmin()) {
			return  DictUtils.getDictList("assistState");
		}
		int roleValue = findRoleValue(user);
		
		if(roleValue == Global.STREET_VALUE||
				roleValue == Global.COMMUNITY_VALUE||
					roleValue == Global.COMMUNITY_VALUE){
			//街道
			return findDict(roleValue);
		}else {
			//以上
			return DictUtils.getDictList("assistState");
		}
	}
	
	//根据用户权限查询帮扶
	public List<Assist> findAssistList(Assist assist){
		User user = UserUtils.getUser();
		if(user.isAdmin()) {
			return assistDao.findList(assist);
		}
		int roleValue = findRoleValue(user);
		if(roleValue == Global.STREET_VALUE){
			//街道
			return findAssistByStreet(user,assist);
		}else if(roleValue == Global.COMMUNITY_VALUE){
			//社区
			return findAssistByCommunity(user,assist);
		}else if(roleValue == Global.COMMUNITY_VALUE){
			//工会
			return findAssistByGh(user,assist);
		}else {
			//以上
			return assistDao.findList(assist);
		}
	}
	
	//工会帮扶
	private List<Assist> findAssistByGh(User user,Assist assist){
		assist.setBeginAssistState(2);
		assist.setEndAssistState(4);
		return assistDao.findList(assist);
	}
	
	//社区帮扶
	private List<Assist> findAssistByCommunity(User user,Assist assist){
		
		//社区
		String communityKey = user.getCommunityKey();
		if(StringUtils.isNotEmpty(communityKey)) {
			assist.setQueryCnk(communityKey);
		}
		assist.setBeginAssistState(0);
		assist.setEndAssistState(1);
		
		return assistDao.findList(assist);
	}
	
	//根据用户权限查询用户
	public List<Userinfo> findUserInfoList(Userinfo userinfo){
		User user = UserUtils.getUser();
		if(user.isAdmin()) {
			return userinfoDao.findList(userinfo);
		}
		int roleValue = findRoleValue(user);
		if(roleValue == Global.STREET_VALUE){
			//街道
			return findUiByStreet(user,userinfo);
		}else if(roleValue == Global.COMMUNITY_VALUE){
			//社区
			String communityKey = user.getCommunityKey();
			if(StringUtils.isNotEmpty(communityKey)) {
				userinfo.setCommunityKey(Integer.valueOf(communityKey));
			}
			return userinfoDao.findList(userinfo);
		}else {
			//工会及以上
			return userinfoDao.findList(userinfo);
		}
	}
	
	//街道权限对应的用户
	public List<Assist> findAssistByStreet(User user,Assist assist){
		String communityKey = user.getCommunityKey();
		if(StringUtils.isEmpty(communityKey)) {
			return null;
		}
		List<String> ckList = findCkBySk(communityKey);
		if(null != ckList && ckList.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0;i<ckList.size();i++) {
				if(i == (ckList.size() - 1)) {
					sb.append(ckList.get(i));
				}else {
					sb.append(ckList.get(i));
					sb.append(",");
				}
			}
			assist.setQueryCnk(sb.toString());
		}
		assist.setBeginAssistState(1);
		assist.setEndAssistState(12);
		return assistDao.findList(assist);		
	}
	
	//街道权限对应的用户
	public List<Userinfo> findUiByStreet(User user,Userinfo userinfo){
		String communityKey = user.getCommunityKey();
		if(StringUtils.isEmpty(communityKey)) {
			return null;
		}
		List<String> ckList = findCkBySk(communityKey);
		if(null != ckList && ckList.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0;i<ckList.size();i++) {
				if(i == (ckList.size() - 1)) {
					sb.append(ckList.get(i));
				}else {
					sb.append(ckList.get(i));
					sb.append(",");
				}
			}
			userinfo.setQueryCnk(sb.toString());
		}
		return userinfoDao.findList(userinfo);
	}
	
	/**
	 * 查询用户
	 * @param page
	 * @param userinfo
	 * @return
	 */
	public Page<Userinfo> findPage(Page<Userinfo> page, Userinfo userinfo) {
		page.setOrderBy("update_date desc");
		userinfo.setPage(page);
		page.setList(findUserInfoList(userinfo));
		return page;
	}
	
	/**
	 * 查询帮扶
	 * @param page
	 * @param userinfo
	 * @return
	 */
	public Page<Assist> findPage(Page<Assist> page, Assist assist) {
		page.setOrderBy("update_date desc");
		assist.setPage(page);
		page.setList(findAssistList(assist));
		return page;
	}
	
	//权限对应的id
	private int findRoleValueById(String roleId) {
		if(roleId.equals(Global.STREET_ID)){
			return Global.STREET_VALUE;
		}else if(roleId.equals(Global.COMMUNITY_ID)){
			return Global.COMMUNITY_VALUE;
		}else {
			return Global.GH_VALUE;
		}
	}
	

	//-- User Service --//
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	public Page<User> findUser(Page<User> page, User user) {
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user){
		List<User> list = userDao.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>)CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null){
			User user = new User();
			list = userDao.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}
	
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (StringUtils.isBlank(user.getId())){
			user.preInsert();
			userDao.insert(user);
		}else{
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		if (StringUtils.isNotBlank(user.getId())){
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userDao.insertUserRole(user);
			}else{
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}
	
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDao.delete(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}
	
	public Role getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleDao.getByEnname(r);
	}
	
	public List<Role> findRole(Role role){
		return roleDao.findList(role);
	}
	
	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}
	
	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleDao.insert(role);
		}else{
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleDao.insertRoleMenu(role);
		}
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	//-- Menu Service --//
	
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}
	
	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {
		
		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds(); 
		
		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())){
			menu.preInsert();
			menuDao.insert(menu);
		}else{
			menu.preUpdate();
			menuDao.update(menu);
		}
		
		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+menu.getId()+",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 "+Global.getConfig("productName"));
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}
	
	///////////////// Synchronized to the Activiti //////////////////
	
	// 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java

	/**
	 * 是需要同步Activiti数据，如果从未同步过，则同步数据。
	 */
	private static boolean isSynActivitiIndetity = true;
	public void afterPropertiesSet() throws Exception {
	}
	
	
	
	///////////////// Synchronized to the Activiti end //////////////////
	
}
