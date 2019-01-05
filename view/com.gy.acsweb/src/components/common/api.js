import util from './util'

let api = {

  // 登录相关
  getDynamicSecretKey: () => util.get('/user/getDynamicSecretKey'),
  login: p => util.postForm('/user/login', p),
  // 角色信息
  listRolePage: p => util.post('/role/listRolePage', p),
  addRole: p => util.post('/role/addRole', p),
  updateRole: p => util.post('/role/updateRole', p),
  deleteRole: p => util.delete('/role/deleteRole', p),
  // 用户相关
  getUserList: p => util.post('/user/listAllUserPage', p),
  addUser: p => util.post('/user/addUser', p),
  updateUser: p => util.put('/user/updateUser', p),
  // 器官配置
  addOrgan: p => util.post('/organ/addOrgan', p),
  updateOrgan: p => util.put('/organ/updateOrgan', p),
  getOrganList: p => util.post('/organ/listOrgan', p),
  deleteOrgan: (id) => util.delete(`/organ/deleteOrgan/${id}`),

  addFeature: p => util.post('/featureType/addFeatureType', p),
  updateFeature: p => util.put('/featureType/updateFeatureType', p),
  getFeatureList: p => util.post('/featureType/listFeatureTypePage', p),
  deleteFeature: (id) => util.delete(`/featureType/deleteFeatureType/${id}`),

  addValue: p => util.post('/organFeature/addOrganFeature', p),
  updateValue: p => util.put('/organFeature/updateOrganFeature', p),
  getValueList: p => util.post('/organFeature/listOrganPage', p),
  deleteValue: (id) => util.delete(`/organFeature/deleteOrganFeature/${id}`),
  // 五官分析
  group: p => util.post('', p),
  organs: p => util.post('', p),

  // 品牌商 | KOL
  addBrand: p => util.postForm('/brand/addBrand', p),
  updateBrand: p => util.put('/brand/updateBrand', p),
  getBrandList: p => util.post('/brand/listBrandPage', p),
  deleteBrand: (brandId, fileId) => util.delete(`/brand/deleteBrand/${brandId}/${fileId}`),

  addKol: p => util.postForm('/kol/addKol', p),
  updateKol: p => util.put('/kol/updateKol', p),
  getKolList: p => util.post('/kol/listKolPage', p),
  deleteKol: (kolId, fileId) => util.delete(`/kol/deleteKol/${kolId}/${fileId}`),

  //文件信息
  addSysFileInfo: p => util.post('/sysFileInfo/addSysFileInfo', p),

  /** 
   * 未调接口
   */

  // 整妆库
  addWholeMakeup: p => util.post('/wholeMakeup/addWholeMakeup', p),
  getWholeMakeup: p => util.post('/wholeMakeup/listWholeMakeupPage', p),
  updateWholeMakeup: p => util.put('/wholeMakeup/updateWholeMakeup', p),
  deleteWholeMakeup: (id) => util.delete(`/wholeMakeup/deleteWholeMakeup/${id}`),

  getMakeupList: p => util.post('/wholeMakeup/listAppCourseZip', p),


};

export default api
