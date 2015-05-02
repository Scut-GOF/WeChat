package gof.scut.fental.models.test;

import java.util.ArrayList;
import java.util.List;

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

	public static List<IdObj> makeContacts() {

		//TODO 生成750条联系人数据，包括姓名，地址，备注
		for (int i = 0; i < 750; i++) {
			//TODO 姓名包括中文，英文，特殊字符（比如颜文字），包括火警，10086等特殊电话
			//TODO 地址可长可短可空，50%空，30%短，20%长，长地址从国到房号，短地址可以只有C12-434
			//TODO 备注50%空，40%短，10%超长（比如一段歌词）
			//TODO 地址空的部分，备注不一定空
		}

		return contacts;
	}

	public static List<String> makeLabels() {

		//TODO 只需要标签名
		//TODO 标签有20个，标签名可以是中文，英文，特殊字符
		return labels;
	}

	public static List<IdTelObj> makeIdTels() {

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
		return idLabels;
	}
}
