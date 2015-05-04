package gof.scut.fental.models.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gof.scut.cwh.models.object.IdLabelObj;
import gof.scut.cwh.models.object.IdObj;
import gof.scut.cwh.models.object.IdTelObj;

/**
 * Created by cwh on 15-5-2.
 */
public class TestData {
	static List<IdObj> contacts = new ArrayList<>();
	static List<String> labels = new ArrayList<>();
	static List<IdTelObj> idTels = new ArrayList<>();
	static List<IdLabelObj> idLabels = new ArrayList<>();
    static String[] faceText = {"(●'◡'●)ﾉ♥", "<(▰˘◡˘▰)>", "｡◕‿◕｡", "(｡・`ω´･)", "(♥◠‿◠)ﾉ", "ʅ(‾◡◝)",     //6个
            "(≖ ‿ ≖)✧", "（´∀｀*)", "（＾∀＾）","", "(o^∇^o)ﾉ", "ヾ(=^▽^=)ノ", "(*￣∇￣*)",      //6个
            "(*´∇｀*)", "(*ﾟ▽ﾟ*)", "(｡･ω･)ﾉﾞ", "(≡ω≡．)", "(｀･ω･´)", "(´･ω･｀)", "(●´ω｀●)φ"};  //7个
    static String[] telPhone = {"10086", "10010", "10000"};
    static String[] longAddresses = {"广东省广州市番禺区大学城华南理工大学生活区"};
    static String[] shortAddresses = {"北京市（京）","天津市（津）","上海市（沪）","重庆市（渝）","河北省（冀）","河南省（豫）",
            "云南省（云）","辽宁省（辽）","黑龙江省（黑）","湖南省（湘）","安徽省（皖）","山东省（鲁）",
            "新疆维吾尔（新）","江苏省（苏）","浙江省（浙）","江西省（赣）","湖北省（鄂）","广西壮族（桂）",
            "甘肃省（甘）","山西省（晋）","内蒙古（蒙）","陕西省（陕）","吉林省（吉）","福建省（闽）",
            "贵州省（贵）","广东省（粤）","青海省（青）","西藏（藏）","四川省（川）","宁夏回族（宁）",
            "海南省（琼）","台湾省（台）","香港特别行政区","澳门特别行政区"};

    static String[] shortNotes = {"他不是   傻逼", "傻 逼应该是他", "的确他是傻逼", "他真的不是傻逼"};
    static String[] longNotes = {"我以为我的温柔能给你整个宇宙，我以为我能权利，填满你感情的缺口；专心陪在你左右，弥补他一切的错。我以为我的温柔能给你整个宇宙，我以为我能权利，填满你感情的缺口；专心陪在你左右，弥补他一切的错。" +
                                            "我以为我的温柔能给你整个宇宙，我以为我能权利，填满你感情的缺口；专心陪在你左右，弥补他一切的错。我以为我的温柔能给你整个宇宙，我以为我能权利，填满你感情的缺口；专心陪在你左右，弥补他一切的错。我以为我的温柔能给你整个宇宙，我以为我能权利，填满你感情的缺口；专心陪在你左右，弥补他一切的错。" +
                                            "我以为我的温柔能给你整个宇宙，我以为我能权利，填满你感情的缺口；专心陪在你左右，弥补他一切的错。我以为我的温柔能给你整个宇宙，我以为我能权利，填满你感情的缺口；专心陪在你左右，弥补他一切的错。" +
                                            "我以为我的温柔能给你整个宇宙，我以为我能权利，填满你感情的缺口；专心陪在你左右，弥补他一切的错。",
                                    "这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，" +
                                            "这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，" +
                                            "这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注，这里是备注。",
                                    "你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。" +
                                            "你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。" +
                                            "你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。" +
                                            "你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。" +
                                            "你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。你才是备注，你全家才是备注。" +
                                            "你才是备注，你全家才是备注。你才是备注，你全家才是备注。"};
    static String[] allLabels = {"傻逼=。=","家人","华南理工大学","网友","My Friendsヾ(=^▽^=)ノ","汕头金山中学","二次元","Hello World","好标签名都被狗起了","万星人"
                                    ,"不包含家长的分组","MONKEY·D·Luffy's 粉丝","爽身粉爱好者","大牛","百家争鸣","草帽海贼团","oo编程","apple bitch","程序猿鼓励师","FE 工程师"};
	public static List<IdObj> makeContacts() {

        int shortAddress = 0;
        int longAddress = 0;
        int nullAddress = 0;

        int shortNote = 0;
        int longNote = 0;
        int nullNote = 0;
		//TODO 生成750条联系人数据，包括姓名，地址，备注
		for (int i = 0; i < 750; i++) {
			//TODO 姓名包括中文，英文，特殊字符（比如颜文字）","，包括火警，10086等特殊电话
			//TODO 地址可长可短可空，50%空，30%短，20%长，长地址从国到房号，短地址可以只有C12-434
			//TODO 备注50%空，40%短，10%超长（比如一段歌词）","
			//TODO 地址空的部分，备注不一定空
            String name = "";
            String lPinYin;
            String sPinYin;
            String tel;
            String address;
            String notes;

            //名字
            int randName = (int)(Math.random()*100);
            if (randName % 3 == 0) {
                name += getRandomChar(4) + " " + getRandomChar(6);
            }
            else if (randName % 7 == 0) {
                name += getRandomChar(4) + "·" + getRandomChar(6);
            }
            else if (randName % 5 == 0) {
                name += getRandomJianHan(3);
            }
            else {
                name += getRandomJianHan(3);
                name += getRandomChar(4);
            }
            //添加颜文字
            if ((int)(Math.random()*100) % 3 == 0) {
                name += faceText[(int)(Math.random()*18)];
            }


            //地址
            int randAddress = (int)(Math.random()*100);
            if (randAddress % 7 == 0 && longAddress < 150) {
                address = longAddresses[0] + "C"+ (int)(Math.random() * 16 + 1) + "-" + (int)(Math.random() * 6 + 1) + (int)(Math.random() * 6) + (int)(Math.random() * 6);
                longAddress++;
            }
            else if (randAddress % 5 == 0 && shortAddress < 225) {
                address = "C"+ (int)(Math.random() * 16 + 1) + "-" + (int)(Math.random() * 6 + 1) + (int)(Math.random() * 6) + (int)(Math.random() * 6);
                shortAddress++;
            }
            else {
                nullAddress++;
                if (nullAddress >= 500) {
                    if (longAddress < 150) {
                        address = shortAddresses[(int)(Math.random() * 33)];
                        longAddress++;
                    }
                    else {
                        address = shortAddresses[(int)(Math.random() * 33)];
                        shortAddress++;
                    }
                }
                else {
                    address = null;
                }
            }

            //备注
            int randNote = (int)(Math.random()*100);
            if (randNote % 3 == 0 && longNote < 150) {
                notes = longNotes[(int)(Math.random() * 2)];
                longNote++;
            }
            else if (randNote % 5 == 0 && shortNote < 225) {
                notes = shortNotes[(int)(Math.random() * 3)];
                shortNote++;
            }
            else {
                nullNote++;
                if (nullNote >= 500) {
                    if (longNote < 150) {
                        notes = longNotes[(int)(Math.random() * 2)];
                        longNote++;
                    }
                    else {
                        notes = shortNotes[(int)(Math.random() * 3)];
                        shortNote++;
                    }
                }
                else {
                    notes = null;
                }
            }
            IdObj myIdObj = new IdObj();
            myIdObj.setName(name);
            myIdObj.setAddress(address);
            myIdObj.setNotes(notes);

            contacts.add(myIdObj);
		}
		return contacts;
	}

	public static List<String> makeLabels() {

		//TODO 只需要标签名
		//TODO 标签有20个，标签名可以是中文，英文，特殊字符
        for (int i = 0; i < 20; i++) {
            String label = allLabels[i];

            labels.add(label);
        }
		return labels;
	}

	public static List<IdTelObj> makeIdTels() {
        for (int i = 1; i < 751; i++) {
            String id = "" + i;
            String tel;
            //电话i
            int randTel = (int)(Math.random()*100);
            if (randTel % 2 == 0) {
                //手机
                tel = "" + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9)
                        + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9)
                        + (int)(Math.random() * 9) + (int)(Math.random() * 9);
            }
            else if (randTel % 3 == 0) {
                //带区号的号码
                tel = "" + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9)
                        + "-" + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9)
                        + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9);
            }
            else if (randTel % 7 == 0) {
                tel = "" + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9)
                        + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9);
            }
            else {
                tel = telPhone[(int)(Math.random()*2)];
            }

            IdTelObj myIdTels = new IdTelObj(id, tel);
            idTels.add(myIdTels);

            int twoTel = (int)(Math.random()*100);
            if (twoTel % 7 == 0) {
                tel = "" + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9)
                        + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9)
                        + (int)(Math.random() * 9) + (int)(Math.random() * 9);
                IdTelObj SecondIdTels = new IdTelObj(id, tel);
                idTels.add(SecondIdTels);
            }
            int threeTel = (int)(Math.random()*100);
            if (threeTel % 11 == 0) {
                tel = "" + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9)
                        + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9) + (int)(Math.random() * 9)
                        + (int)(Math.random() * 9) + (int)(Math.random() * 9);
                IdTelObj ThirdIdTels = new IdTelObj(id, tel);
                idTels.add(ThirdIdTels);
            }

        }
		//TODO ID必须在contacts中存在
		//TODO tel有的是电话，有的是手机，有的带区号，有的是10086等特殊号码
		return idTels;
	}

	public static List<IdLabelObj> makeIdLabels() {
		//TODO id必须在contacts中存在
		//TODO label必须在contacts中存在
		//TODO 只有40%的联系人是有Label的
		//TODO 在这40%的联系人中
		//TODO 50%有1个label，20%有2个label，20%有3个label，10%有4-20个label
        for (int i = 1; i < 751; i++) {
            if ((int)(Math.random() * 100) < 40) {
                String id = "" + i;
                String label;
                int[] tmpRandLabel = new int[20];

                int tmp = (int)(Math.random() * 10);
                if (tmp % 2 == 0) {
                    label = labels.get((int)(Math.random() * 19));
                    IdLabelObj myIdLabelObj1 = new IdLabelObj(id, label);
                    idLabels.add(myIdLabelObj1);
                }
                else if (tmp % 3 == 0) {
//                    label = labels.get((int)(Math.random() * 19));
                    for (int j = 0; j < 2; j++) {
                        tmpRandLabel[j] = (int)(Math.random() * 19);
                        for (int k = j - 1; k >= 0; k--) {
                            if (tmpRandLabel[j] == tmpRandLabel[k]) {
                                tmpRandLabel[j] = -1;
                            }
                        }
                        if (tmpRandLabel[j] != -1) {
                            label = labels.get(tmpRandLabel[j]);
                            IdLabelObj myIdLabelObj2 = new IdLabelObj(id, label);
                            idLabels.add(myIdLabelObj2);
                        }
                    }
                }
                else if (tmp % 5 == 0) {
                    for (int j = 0; j < 2; j++) {
                        tmpRandLabel[j] = (int)(Math.random() * 19);
                        for (int k = j - 1; k >= 0; k--) {
                            if (tmpRandLabel[j] == tmpRandLabel[k]) {
                                tmpRandLabel[j] = -1;
                            }
                        }
                        if (tmpRandLabel[j] != -1) {
                            label = labels.get(tmpRandLabel[j]);
                            IdLabelObj myIdLabelObj2 = new IdLabelObj(id, label);
                            idLabels.add(myIdLabelObj2);
                        }
                    }
                }
                else if (tmp % 10 == 0) {
                    for (int j = 0; j < (int)(Math.random() * 16); j++) {
                        tmpRandLabel[j] = (int)(Math.random() * 19);
                        for (int k = j - 1; k >= 0; k--) {
                            if (tmpRandLabel[j] == tmpRandLabel[k]) {
                                tmpRandLabel[j] = -1;
                            }
                        }
                        if (tmpRandLabel[j] != -1) {
                            label = labels.get(tmpRandLabel[j]);
                            IdLabelObj myIdLabelObj2 = new IdLabelObj(id, label);
                            idLabels.add(myIdLabelObj2);
                        }
                    }
                }
            }
        }
		return idLabels;
	}

    //随机生成中文
    public static String getRandomJianHan(int len)
    {
        String ret="";
        for(int i=0;i<len;i++){
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try
            {
                str = new String(b, "GB2312"); //转成中文
            }
            catch (UnsupportedEncodingException ex)
            {
                ex.printStackTrace();
            }
            ret+=str;
        }
        return ret;
    }
    public static String getRandomChar(int len) {
        String ret = "";
        //随机生成字母
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char[] c = s.toCharArray();
        char a = 'b';
        String temp = "";
        for (int i = 0; i < len; i++) {
            ret += temp.valueOf(c[(int)(Math.random() * 52)]);
        }
        return ret;
    }
    public static void main(String[]args) {
        List<IdObj> cons = new ArrayList<>();
        List<IdTelObj> idTels = new ArrayList<>();
        List<IdLabelObj> idLabel = new ArrayList<>();

//        cons = makeContacts();
        idTels = makeIdTels();
        makeLabels();
        idLabel = makeIdLabels();
        System.out.println(labels.size());
        for (int i = 0; i < labels.size(); i++)  {
            System.out.println(labels.get(i));
        }
        for (int i = 0; i < idLabel.size(); i++)  {
            System.out.println(idLabel.get(i).getId() + " " + idLabel.get(i).getLabel());
        }
    }
}