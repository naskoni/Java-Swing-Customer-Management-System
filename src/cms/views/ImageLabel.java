package cms.views;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageLabel extends JLabel {

	private static final long serialVersionUID = -4398639261717716734L;

	private Image logoImage;

	public ImageLabel() {
		super();
	}

	@Override
	public void setIcon(Icon icon) {
		super.setIcon(icon);
		if (icon instanceof ImageIcon) {
			logoImage = ((ImageIcon) icon).getImage();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(logoImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
