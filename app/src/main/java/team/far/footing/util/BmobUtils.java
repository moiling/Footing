package team.far.footing.util;

import cn.bmob.v3.BmobUser;
import team.far.footing.app.APP;
import team.far.footing.model.bean.Userbean;

/**
 * Created by Luoyy on 2015/8/8 0008.
 */
public class BmobUtils {


    /**
     * @return 需要在登陆后才可以调用
     */
    public static Userbean getCurrentUser() {
        return BmobUser.getCurrentUser(APP.getContext(), Userbean.class);

    }

    /**
     *   退出登录
     */
    public static void LogOutUser() {
        BmobUser.logOut(APP.getContext());
    }

    public static String searchCode(int i) {
        switch (i) {
            case 101:
                return "帐号或密码错了！再仔细看看";
            case 102:
                return "是不是关键字输错了？";
            case 103:
                return "是不是关键字输错了？";
            case 104:
                return "没有这个东西！";
            case 105:
                return "是不是关键字输错了？";
            case 106:
                return "不是一个正确的指针类型";
            case 107:
                return "不是正确的JSON格式";
            case 108:
                return "帐号密码必须要啊！";
            case 109:
                return "有些该填的信息没有填哦";
            case 111:
                return "传入的字段不匹配";
            case 112:
                return "requests的值必须是数组";
            case 113:
                return "requests数组中元素格式不对";
            case 114:
                return "requests数组太大了";
            case 117:
                return "纬度范围在[-90, 90] 或 经度范围在[-180, 180]";
            case 120:
                return "邮箱认证功能没有开启";
            case 131:
                return "不正确的deviceToken";
            case 132:
                return "不正确的installationId";
            case 133:
                return "不正确的deviceType";
            case 134:
                return "deviceToken已经存在";
            case 135:
                return "installationId已经存在";
            case 136:
                return "只读属性不能修改 或 android设备不需要设置deviceToken";
            case 138:
                return "表单是只读模式";
            case 139:
                return "角色名称已经存在或者格式不对";
            case 141:
                return "缺失推送需要的data参数";
            case 142:
                return "时间格式应该如下： 2013-12-04 00:51:13";
            case 143:
                return "必须是一个数字才可以";
            case 144:
                return "这一天貌似在未来吧？";
            case 145:
                return "文件大小错误";
            case 146:
                return "文件名错误";
            case 147:
                return "文件分页上传偏移量错误";
            case 148:
                return "文件上下文错误";
            case 149:
                return "空文件";
            case 150:
                return "文件上传错误";
            case 151:
                return "文件删除错误";
            case 160:
                return "图片错误";
            case 161:
                return "图片模式错误";
            case 162:
                return "图片宽度错误";
            case 163:
                return "图片高度错误";
            case 164:
                return "图片长边错误";
            case 165:
                return "图片短边错误";
            case 201:
                return "缺失数据";
            case 202:
                return "好可惜，这个帐号已经被人注册了";
            case 203:
                return "该邮箱已经注册过了，是不是以前注册过呢？试着找回密码吧！";
            case 204:
                return "必须填写邮箱地址哦";
            case 205:
                return "没有找到这个人";
            case 206:
                return "Http Header中没有提供sessionToken的正确值，不能修改或删除用户";
            case 207:
                return "验证码错误";
            case 208:
                return "authData不正确";
            case 209:
                return "该手机号已经注册过了，是不是以前注册过呢？试着找回密码吧！";
            case 301:
                return "邮箱格式不正确";
            case 310:
                return "被限定了！";
            case 311:
                return "";
            case 401:
                return "";
            case 402:
                return "";
            case 601:
                return "";
            case 1002:
                return "";
            case 1003:
                return "";
            case 1004:
                return "";
            case 1005:
                return "";
            case 1006:
                return "";
            case 1007:
                return "";
            case 1500:
                return "";
            case 10001:
                return "";
            case 10002:
                return "";
            case 10003:
                return "";
            case 10004:
                return "";
            case 10010:
                return "";
            case 10011:
                return "";
            case 10012:
                return "";
            case 10013:
                return "";
        }
        return "我也不知道怎么就错了~诶嘿~";
    }
}
