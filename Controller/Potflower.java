package Controller;

import javafx.beans.property.*;
import java.util.UUID;

public class Potflower {
	private SimpleIntegerProperty pcs, id;
	private SimpleStringProperty name;
	private SimpleDoubleProperty height, width, weight, price;
	
	public Potflower(int id, String name, double height, double width, double weight,int pcs, double price)
	{
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.height = new SimpleDoubleProperty(height);
		this.width = new SimpleDoubleProperty(width);
		this.weight = new SimpleDoubleProperty(weight);
		this.pcs = new SimpleIntegerProperty(pcs);
		this.price = new SimpleDoubleProperty(price);
	}
	
	public int getID()
	{
		return id.get();
	}
	
	public String getName()
	{
		return name.get();
	}
	
	public double getHeight()
	{
		return height.get();
	}
	
	public double getWidth()
	{
		return width.get();
	}
	
	public double getWeight()
	{
		return weight.get();
	}
	
	public int getPcs()
	{
		return pcs.get();
	}
	
	public double getPrice()
	{
		return price.get();
	}
	
	public void setID(int id)
	{
		this.id.set(id);
	}
	
	public void setName(String name)
	{
		this.name.set(name);
	}
	
	public void setHeight(double height)
	{
		this.height.set(height);
	}
	
	public void setWidth(double width)
	{
		this.width.set(width);
	}
	
	public void setWeight(double weight)
	{
		this.weight.set(weight);
	}
	
	public void setPcs(int pcs)
	{
		this.pcs.set(pcs);
	}
	
	public void setPrice(double price)
	{
		this.price.set(price);
	}	
	

}
