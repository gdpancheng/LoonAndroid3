# LoonAndroid 3.0

Loonandroid是一个注解框架，不涉及任何UI效果，目的是一个功能一个方法，以方法为最小颗粒度对功能进行拆解。把功能傻瓜化，简单化，去掉重复性的代码，隐藏复杂的实现。以便团队合作或者后期修改变得简单。说框架是夸大了，主要是因为我比较喜欢偷懒，对于一个码农来说，能够偷懒，并且在不影响项目质量的情况下，是不容易的。

- - -

很多朋友看到注解就就要吐槽，会影响性能什么的。注解，确实会影响性能。通过注解自动注入，反射会让程序变慢50~100毫秒左右，从体验感基本感觉不出来.硬件性能好的手机可以忽略，经过测试无需太大的担心。我是做外包的，初衷是在不影响项目质量的前提下减少我的工作量，而且BUG其他人改起来相对比较容易，本工具专属外包码农，如果你想做精细，很在意性能数据，请看看就好。
- - -
`LoonAndroid 3` 是LoonAndroid改良版，之前的版本存在内存无法释放的问题。增加了一些新的功能，让开发变得非主流。


**特别声明：版本3x框架还未在实际生产中验证，目前只拥有demo和我改造的一个项目，请小心谨慎，如有使用，请告知我，我会跟踪相应BUG以及性能，以做到及时修复。**


[TOC]

## release 3.0
**1、基本功能**

- InLayer注解 
- InPlayer 注解
- Activity生命周期注解
- InView注解
- InSource注解
- InAll注解
- 后台进程注解
- 方法点击事件注解
- 基类注解
- 自动Fragment注解
- 手动Fragment注解

**2、适配器功能**

- 无适配器
- 无参baseAdapter
- 自定义一adapter
- 自定义二adapter
- 自动绑定一adapter
- 自动绑定二adapter
- 通用适配器 

**3、综合功能集合**

- 网络请求模块
- 输入验证
- 跨进程通讯
- Json格式化类
- 倒计时类

**4、傻瓜式下拉刷新**

- Listview
- Grid
- 横向Scrollview
- 纵向Scrollview
- 横向ViewPage
- 纵向ViewPage
- WebView

**5、自定义模块类**

- 自定义模块XML中使用
- 自定义模块变量使用

**6、傻瓜式组件类**

- 获取图片组件
- 登录组件

## 使用
1. 在项目的Application中进行初始化
2. 在assets目录下面mvc.properties的配置设置如下
3. 引入loonandroid最新版jar以及依赖包dex.jar
```
public class App extends Application {
    @Override
	public void onCreate() {
		app = this;
		Ioc.getIoc().init(this);
		super.onCreate();
	}
}
```
```
#---------------------------框架基础配置-----------------------------
#配置当前屏幕基于哪个分辨率开发 框架里面所有缩放比例全部来源于此 默认 480 800
standard_w=720
standard_h=1280
#-------------------------设置只允许加载到框架中的包名---------------
#如果不设置，那么默认遍历Manifest中的package，多个可以以逗号隔开
permit=com.android.demo,com.loonandroid.pc.plug
#--------------------------设置不允许解析的包名------------------
#如果不设置，那么默认遍历Manifest中的package，多个可以以逗号隔开
limit=com.example.loonandroid2.R
```
## 示例
**1 为你去掉繁琐的findViewById**<br><br>
`平时我们这么写`
```
public class MyActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_demo);
            findViewById();
        }
        void findViewById(){
        .....
        .....
        .....
        .....
        };
	}
```
`现在我们这么写`
```
@InLayer(R.layout.welcome)
public class WelcomeActivity extends Activity {
		// ----------------------------------------------
		// View
		@InAll
		Views v;
        static class Views {
            public ViewFlow flow;
            public CircleFlowIndicator circle;
        }
}
```
**2 获取照片**<br><br>
`以前我们这么写`
```
点击事件
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.camera:
		Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 下面这句指定调用相机拍照后的照片存储的路径
		intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), Constant.save_user_photo)));
		startActivityForResult(intentCamera, 2);
		bottomPhotoDialog.dismiss();
		break;
	case R.id.photo:
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, 1);
		bottomPhotoDialog.dismiss();
		break;
	case R.id.cancle:
		bottomPhotoDialog.dismiss();
		break;
	}
}
裁剪参数
private void startPhoto(Uri url) {
	Intent intent = new Intent();
	intent.putExtra(Util.IMAGE_URI, url);
	intent.putExtra(Util.CROP_IMAGE_WIDTH, 300);
	intent.putExtra(Util.CROP_IMAGE_HEIGHT, 300);
	intent.putExtra(Util.CIRCLE_CROP, false);
	intent.setClass(this, CropActivity.class);
	startActivityForResult(intent, 3);
}
activity回调
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	switch (requestCode) {
	// 如果是直接从相册获取
	case 1:
		if (data == null) {
			return;
		}
		Uri uri = data.getData();
		if (uri != null) {
			startPhoto(uri);
		}
		break;
	// 如果是调用相机拍照时
	case 2:
		picture = new File(Environment.getExternalStorageDirectory() + "/" + Constant.save_user_photo);
		if (!picture.exists()) {
			return;
		}
		Uri uri2 = Uri.fromFile(picture);
		if (uri2 != null) {
			startPhoto(uri2);
		}
		break;
	// 取得裁剪后的图片
	case 3:
		if (data != null) {
			bitmap = BitmapFactory.decodeFile(data.getStringExtra(Util.CROP_IMAGE_PATH));
			user_photo.setImageBitmap(bitmap);
			upload_head(data.getStringExtra(Util.CROP_IMAGE_PATH));
		}
		break;
	default:
		break;
	}
	super.onActivityResult(requestCode, resultCode, data);
}
```
`现在我们这么写`
```
@InLayer(R.layout.activity_getphoto)
public abstract class GetPhotoActivity extends Activity implements PluginPhoto {

	@InAll
	Views test;

	class Views {
		ImageView iv_photo;
		@InBinder(listener = OnClick.class, method = "click")
		Button bt_photo, bt_camera;
	}

    private void click(View v) {
		switch (v.getId()) {
		case R.id.bt_photo:
			//从相册获取图片
			PhotoConfig config = new PhotoConfig();
			config.aspectX = 1;
			config.aspectY = 2;
			config.outputX = 200;
			config.outputY = 400;
			photo(config);
			break;
		case R.id.bt_camera:
			//从相机获取图片
			camera();
			break;
		}
	}

	@Override
	public void callBack(Object... args) {
		Toast.makeText(this, "图片路径："+args[1], Toast.LENGTH_SHORT).show();
		System.out.println("-----------------------------");
		test.iv_photo.setImageBitmap((Bitmap)args[0]);
	}
}
```
**3 登录**<br><br>
`以前这么写`
```
public class LoginActivity extends Acitivity {

	TextView login_bt, register;
	EditText user_name, user_password, user_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		login_bt = (TextView) findViewById(R.id.login_bt);
		register = (TextView) findViewById(R.id.register);
		user_name = (EditText) findViewById(R.id.user_name);
		user_password = (EditText) findViewById(R.id.user_password);
		user_code = (EditText) findViewById(R.id.user_code);
	}

	/**
	 * 网络请求回调
	 */
	AjaxCallBack callBack = new AjaxCallBack() {
		@Override
		public void callBack(ResponseEntity status) {
			progressDimss();
			switch (status.getStatus()) {
			case FastHttp.result_ok:
				HashMap<String, Object> data = JsonUtil.initJson(status.getContentAsString());
				if (data.get("status").toString().equals("0")) {
					showToast(data.get("data").toString());
				} else {
					App.app.setData("user_id", data.get("data").toString());
					startActivity(new Intent(LoginActivity.this, MainActivity.class));
					overridePendingTransition(0, 0);
					finish();
				}
				break;
			default:
				showToast("连接失败，请检查网络后重试");
				break;
			}
		}
		@Override
		public boolean stop() {
		    return false;
		}
	};

	public void click(View v) {
		switch (v.getId()) {
		case R.id.login_bt:
			hideSoft(user_name);
			hideSoft(user_password);

			String name = user_name.getText().toString().trim();
			String password = user_password.getText().toString().trim();
			String code = user_code.getText().toString().trim();
			if (name.length() == 0) {
				showToast("用户名不能为空");
				return;
			}
			if (password.length() == 0) {
				showToast("密码不能为空");
				return;
			}
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("username", name);
			params.put("password", password);
			showProgress();
			FastHttp.ajax(Constant.url_login, params, callBack);
			break;

		case R.id.register:
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			overridePendingTransition(0, 0);
			break;
		}
	}
}
```
`现在这么写`
```
会自动填入之前的用户名密码 会自动验证 只要调用save()即可存储登录框中信息
只需要调用AccountEntity datas = getSave();即可获得所有存储的账号信息
@InLayer(R.layout.activity_login)
public abstract class LoginActivity extends Activity implements PluginLogin{

	@Override
	public void i(LoginConfig config) {
		config.init(R.id.ed_number, R.id.ed_password, R.id.ed_submit, R.id.ed_remember);
	}

	/**
	 * 当点击登陆按钮，会自动获取输入框内的用户名和密码，对其进行验证
	 */
	@Override
	public void onValiResult(View view) {
		if (view == null) {
	        //验证通过
			App.app.http.u(this).login("aaa", "bbb");
        }else{
        	//验证失败给出提示语
        	Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
        }
	}

	@InHttp(HttpUrl.LOGIN_KEY)
	public void result(ResponseEntity entity){
		if (entity.getStatus() == FastHttp.result_net_err) {
			Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
			return;
		}
		if (entity.getContentAsString()==null||entity.getContentAsString().length()==0) {
			Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
			return;
        }
		//解析返回的数据
		HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
		int status = Integer.valueOf(data.get("status").toString());
		if (status == 0) {
			Toast.makeText(this, data.get("data").toString(), Toast.LENGTH_SHORT).show();
			return;
        }
		save();
		//清除保存的数据
		//clear("bbb");清除账号bbb的缓存
		//clear();清除所有缓存
	}
}
```
**4 自动验证输入框**<br><br>
`平时我们这么写`
```
String name = user_name.getText().toString().trim();
String email = user_email.getText().toString().trim();
String mobile = user_mobile.getText().toString().trim();
String password = user_password.getText().toString().trim();
if (name.length() == 0) {
	showToast("用户名不能为空");
	return;
}
if (password.length() == 0) {
	showToast("密码不能为空");
	return;
}
if (password.length() < 6) {
	showToast("密码长度必须大于6位");
	return;
}
if (email.length() == 0) {
	showToast("邮箱不能为空");
	return;
}
if (mobile.length() == 0) {
	showToast("手机号码不能为空");
	return;
}
if (!deal.isChecked()) {
	showToast("请先同意用户协议");
	return;
}
```
`现在我们这么写`
```
static class Views {
	@InVa(value=VaPassword.class,index=1)
	EditText tv_password;
	@InVa(value=VaPasswordConfirm.class,index=2)
	EditText tv_passwordconfirm;
	@InVa(value=VaEmail.class,index=3)
	EditText tv_email;
	@InVa(value=VaMobile.class,index=4)
	EditText tv_mobile;
	@InVa(value=VaDate.class,index=5)
	EditText tv_data;
	@InVa(value=VaWeb.class,index=6)
	EditText tv_web;
	@InVa(value=VaCard.class,index=7)
	EditText tv_card;
	@InVa(msg = "不能为空",empty=false,index=8)
	EditText tv_notnull;
	@InVa(reg=Regex.LET_NUM_UNLINE_REG,msg="请输入字母数字或下划线",empty=false,index=9)
	EditText tv_number;
	@InBinder(listener=OnClick.class,method="click")
	Button bt_onclick;
}
public void click(View view) {
	Validator.verify(this);
}
@InVaOK
private void onValidationSucceeded() {
	Toast.makeText(this, "验证成功", Toast.LENGTH_SHORT).show();
}
@InVaER
public void onValidationFailed(ValidatorCore core) {
	if(TextView.class.isAssignableFrom(core.getView().getClass())){
        EditText editText = core.getView();
        editText.requestFocus();
        editText.setFocusable(true);
        editText.setError(core.getMsg());
    }
}
```

**5 后台进程**
`启动以前我们这么写`
```
public class LanunchActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    new Thread(new Runnable() {
			@Override
			public void run() {
				try {
	                Thread.sleep(3000);
                } catch (InterruptedException e) {
	                e.printStackTrace();
                }
				startActivity(new Intent(WelecomeActivity.this, MenuActivity.class));
				finish();
			}
		}).start();
	}
}
```
`启动页现在我们这么写`
```
@InLayer(R.layout.activity_first)
public class WelecomeActivity extends Activity {
	@Init@InBack
	protected void init() throws InterruptedException {
		Thread.sleep(3000);
		startActivity(new Intent(WelecomeActivity.this, MenuActivity.class));
		finish();
	}
}
```
**6 Fragment优化**
`现在我们这么写`
```
@InLayer(value = R.layout.activity_fragment)
public class AutoFragmentActivity extends FragmentActivity {

	/**
	 * {@link InBean}创建了一个Fragment 无需方法onCreateView
	 */
	@InBean
	private AutoFragment fragment;

	@Init
	void init() {
		System.out.println(fragment);
		startFragmentAdd(fragment);
	}

    public void startFragmentAdd(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_test, fragment);
        fragmentTransaction.commit();
    }
}


@InLayer(R.layout.activity_com)
public class AutoFragment extends Fragment {

	@InView(binder = @InBinder(listener = OnClick.class, method = "click"))
	Button top;

	@Init
	void init() {
		System.out.println("fragment 初始化完毕");
	}

	@InBack
	private void click(View view) {
		System.out.println("这里点击以后进入后台进程");
	}

	@InListener(ids={R.id.top,R.id.bottom},listeners={OnClick.class})
	private void l(View view){
		Toast.makeText(view.getContext(), "父类中点击了", Toast.LENGTH_SHORT).show();
	}
}
```
还有很多比较有意思的功能
1 跨进程通讯 集成了tinybus 无需在activity和fragment中注册即可食用
2 adapter去掉了很多不需要的代码
3 网络请求可以切换网络核心，框架只负责分发，力求傻瓜化，来适应外包敏捷开发
等等

##感谢以下项目
`LoonAndroid 1X 已经上线并告诉我的项目`<br>
**其中面吧是一个转为码农找工作，面试，做面试题的神器，希望大家多多支持<br>**
![](https://raw.githubusercontent.com/gdpancheng/android_frame/master/xincaifu.png)[酷鱼: 新财富集团的APP 资本人的社交圈](http://a.app.qq.com/o/simple.jsp?pkgname=cn.bluemobi.xcf)<br>
![](https://raw.githubusercontent.com/gdpancheng/android_frame/master/baobeijihua.png)[宝贝计划：“宝贝计划”是大连地区专注于儿童教育领域的APP。](http://apk.hiapk.com/appinfo/com.zq.kplan)<br>
![](https://raw.githubusercontent.com/gdpancheng/android_frame/master/bengfatong.png)[泵阀通：是由明作网络在移动互联网领域针对泵阀产业推出的跨时代新媒体](http://www.75g.cn/android/yueduxuexi/117289.html)<br>
![](https://raw.githubusercontent.com/gdpancheng/android_frame/master/dagexing.jpg)[大歌星：娱乐K歌应用，把手机成为录间棚，原版伴奏供你选择。](http://www.liqucn.com/rj/104422.shtml)<br>
![](https://raw.githubusercontent.com/gdpancheng/android_frame/master/gudao.png)[古道网：是一款记录、分享古代道路的手机应用](http://store.lenovo.com/appdetail/com.bluemobi.gudao/0)<br>
![](https://raw.githubusercontent.com/gdpancheng/android_frame/master/mianba.png)[面吧：一个朋友开发的软件，转为程序员面试使用的神器，帮忙宣传下，谢谢。](http://www.wandoujia.com/apps/com.pc.knowledge)<br>
![](https://raw.githubusercontent.com/gdpancheng/android_frame/master/paishouba.png)[拍手吧：高品质音乐，独特的游戏，海量电子书，超好玩的应用软件以及社交体验](http://apk.hiapk.com/appinfo/com.smartions.ps8web)<br>

##有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件(gdpancheng#gmail.com, 把#换成@)
* 微信: gdpancheng
* weibo: [@码农无码](http://weibo.com/u/2426174994)

##捐助开发者
有意也好，无意也罢，至少写了一个东西，有欣喜，也还有汗水，有谩骂，有沮丧，希望你喜欢我的作品，同时也能支持一下。
![](https://raw.githubusercontent.com/gdpancheng/android_frame/master/zhifubao.png)
![](https://raw.githubusercontent.com/gdpancheng/android_frame/master/weixin.png)
当然，有钱捧个钱场，没钱捧个人场，谢谢各位。