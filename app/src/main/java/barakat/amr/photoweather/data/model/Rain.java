package barakat.amr.photoweather.data.model;

import com.google.gson.annotations.SerializedName;

public class Rain{

	@SerializedName("3h")
	private int jsonMember3h;

	public void setJsonMember3h(int jsonMember3h){
		this.jsonMember3h = jsonMember3h;
	}

	public int getJsonMember3h(){
		return jsonMember3h;
	}
}