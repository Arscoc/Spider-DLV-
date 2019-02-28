import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {

	static Image selUp;
	static Image selSide;
	static Image tipUp;
	static Image tipSide;
	static Image front;
	static Image back;

	public ImageLoader() {

	}
//=================================================================================================================================
	public static Image Back() {
		try {
			back = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("res/back.png"))
					.getScaledInstance(Values.CardDimensionX, Values.CardDimensionY, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return back;
	}
//=================================================================================================================================
	public static Image Front(String s) {
		try {
			front = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("res/" + s + ".png"))
					.getScaledInstance(Values.CardDimensionX, Values.CardDimensionY, java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return front;
	}
//=================================================================================================================================
	public static Image selSide(int y) {
	
		try {
			selSide = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("res/selside.png"))
					.getScaledInstance(Values.CardDimensionX + 10, Values.CardDimensionY + (20 * (y-1)),
							java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selSide;
	}
//=================================================================================================================================
	public static Image selUp()
	{
		try {
			selUp = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("res/selup.png"))
					.getScaledInstance(Values.CardDimensionX + 10,5,
							java.awt.Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selUp;
	}
	//=================================================================================================================================
		public static Image tipSide(int y) {
		
			try {
				tipSide = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("res/tipside.png"))
						.getScaledInstance(Values.CardDimensionX + 10, Values.CardDimensionY + (20 * (y-1)),
								java.awt.Image.SCALE_SMOOTH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tipSide;
		}
	//=================================================================================================================================
		public static Image tipUp()
		{
			try {
				tipUp = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("res/tipup.png"))
						.getScaledInstance(Values.CardDimensionX + 10,5,
								java.awt.Image.SCALE_SMOOTH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tipUp;
		}
}
