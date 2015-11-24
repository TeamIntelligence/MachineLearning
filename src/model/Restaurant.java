package model;

import java.util.HashMap;
import java.util.Map;

public class Restaurant {
	
	private boolean alternative;
	private boolean bar;
	private boolean fridaySat;
	private boolean hunger;
	private String pat;
	private String price;
	private boolean rain;
	private boolean res;
	private String est;
	private String type;
	private boolean wait;
	
	public Restaurant(boolean alternative, boolean bar, boolean fridaySat, boolean hunger, String pat, String price,
			boolean rain, boolean res, String est, String type, boolean wait) {
		super();
		this.alternative = alternative;
		this.bar = bar;
		this.fridaySat = fridaySat;
		this.hunger = hunger;
		this.pat = pat;
		this.price = price;
		this.rain = rain;
		this.res = res;
		this.est = est;
		this.type = type;
		this.wait = wait;
	}
	
	public boolean isAlternative() {
		return alternative;
	}
	public void setAlternative(boolean alternative) {
		this.alternative = alternative;
	}
	public boolean isBar() {
		return bar;
	}
	public void setBar(boolean bar) {
		this.bar = bar;
	}
	public boolean isFridaySat() {
		return fridaySat;
	}
	public void setFridaySat(boolean fridaySat) {
		this.fridaySat = fridaySat;
	}
	public boolean isHunger() {
		return hunger;
	}
	public void setHunger(boolean hunger) {
		this.hunger = hunger;
	}
	public String getPat() {
		return pat;
	}
	public void setPat(String pat) {
		this.pat = pat;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public boolean isRain() {
		return rain;
	}
	public void setRain(boolean rain) {
		this.rain = rain;
	}
	public boolean isRes() {
		return res;
	}
	public void setRes(boolean res) {
		this.res = res;
	}
	public String getEst() {
		return est;
	}
	public void setEst(String est) {
		this.est = est;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isWait() {
		return wait;
	}
	public void setWait(boolean wait) {
		this.wait = wait;
	}
	
	public Map<String, String> toMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("alternative", String.valueOf(alternative));
		map.put("fridaySat", String.valueOf(fridaySat));
		map.put("hunger", String.valueOf(hunger));
		map.put("pat", String.valueOf(pat));
		map.put("price", String.valueOf(price));
		map.put("rain", String.valueOf(rain));
		map.put("res", String.valueOf(res));
		map.put("est", String.valueOf(est));
		map.put("type", String.valueOf(type));
		map.put("wait", String.valueOf(wait));
		return map;
	}
	
	@Override
	public String toString() {
		return "Restaurant [alternative=" + alternative + ", bar=" + bar + ", fridaySat=" + fridaySat + ", hunger="
				+ hunger + ", pat=" + pat + ", price=" + price + ", rain=" + rain + ", res=" + res + ", est=" + est
				+ ", type=" + type + ", wait=" + wait + "]";
	}
}
