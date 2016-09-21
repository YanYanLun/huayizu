package yalantis.com.sidemenu.model;

import tang.basic.model.ShortcutButton;
import yalantis.com.sidemenu.interfaces.Resourceble;

/**
 * Created by Konstantin on 23.12.2014.
 */
public class SlideMenuItem implements Resourceble {
	private String name;
	private int imageRes;
	private String title;
	private ShortcutButton button;

	public SlideMenuItem(String name, int imageRes) {
		this.name = name;
		this.imageRes = imageRes;
	}

	public SlideMenuItem(String name, int imageRes, String title) {
		this.name = name;
		this.imageRes = imageRes;
		this.title = title;
	}

	public SlideMenuItem(String name, int imageRes, String title,
			ShortcutButton button) {
		this.name = name;
		this.imageRes = imageRes;
		this.title = title;
		this.button = button;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImageRes() {
		return imageRes;
	}

	public void setImageRes(int imageRes) {
		this.imageRes = imageRes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ShortcutButton getButton() {
		return button;
	}

	public void setButton(ShortcutButton button) {
		this.button = button;
	}

}
