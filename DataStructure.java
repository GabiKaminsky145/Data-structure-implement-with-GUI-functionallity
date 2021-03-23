import java.util.ArrayList;
import java.util.Arrays;

public class DataStructure implements DT {

	ArrayList<Point> points;

	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public DataStructure()
	{
		points = new ArrayList<>();
	}

	@Override
	public void addPoint(Point point) {

		points.add(point);
	}

	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
		ArrayList<Point> pointArrayList = new ArrayList<>();
		for (int i = 0; i < points.size() ; i++) {
			if(axis){
				if (points.get(i).getX() >= min && points.get(i).getX() <= max)
					pointArrayList.add(points.get(i));
			}
			else {
				if (points.get(i).getY() >= min && points.get(i).getY() <= max)
					pointArrayList.add(points.get(i));
			}
		}

		return my_sort(pointArrayList,axis);
	}

	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
		ArrayList<Point> pointArrayList = new ArrayList<>();
		for (int i = 0; i < points.size() ; i++) {
			if(axis){
				if (points.get(i).getX() >= min && points.get(i).getX() <= max)
					pointArrayList.add(points.get(i));
			}
			else {
				if (points.get(i).getY() >= min && points.get(i).getY() <= max)
					pointArrayList.add(points.get(i));
			}
		}

		return my_sort(pointArrayList,!axis);
	}


	public int[] max_min() {
		int max_x = Integer.MIN_VALUE;
		int min_x = Integer.MAX_VALUE;
		int max_y = Integer.MIN_VALUE;
		int min_y = Integer.MAX_VALUE;

		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getX() > max_x)
				max_x = points.get(i).getX();
			if (points.get(i).getX() < min_x)
				min_x = points.get(i).getX();
			if (points.get(i).getX() > max_y)
				max_y = points.get(i).getY();
			if (points.get(i).getX() < min_y)
				min_y = points.get(i).getY();
		}
		return new int[]{max_x, min_x, max_y, min_y};
	}

	@Override
	public double getDensity() {
		int[] max_min = max_min();

		return (points.size())/((max_min[0]-max_min[1])*(max_min[2]-max_min[3]));

	}

	@Override
	public void narrowRange(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
		for (int i = 0; i < points.size() ; i++) {
			if (axis){
				if (points.get(i).getX() > max || points.get(i).getX() < min)
					points.remove(i);
			}
			else {
				if (points.get(i).getY() > max || points.get(i).getY() < min)
					points.remove(i);
			}
		}
	}

	@Override
	public Boolean getLargestAxis() {
		// TODO Auto-generated method stub
		int[] max_min = max_min();
		return (max_min[0] - max_min[1]) > (max_min[2] - max_min[3]);
	}

	@Override
	public Container getMedian(Boolean axis) {
		// TODO Auto-generated method stub
		Point[] med_points = my_sort(points,axis);
		Container ret_med = new Container(med_points[med_points.length/2]);

		return ret_med;


	}

	@Override
	public Point[] nearestPairInStrip(Container container, int width,
									  Boolean axis) {
		// TODO Auto-generated method stub
		ArrayList<Point> nearest = new ArrayList<>();
		double min_diff = Integer.MAX_VALUE;
		Point[] ret_points = new Point[2];
		if(axis){
			int max = container.getData().getX() + width/2;
			int min = container.getData().getX() - width/2;
			for (Point point : points) {
				if (point.getX() >= min && point.getX() <= max)
					nearest.add(point);
			}
		}
		else{
			int max = container.getData().getY() + width/2;
			int min = container.getData().getY() - width/2;
			for (Point point : points) {
				if (point.getY() >= min && point.getY() <= max)
					nearest.add(point);
			}
		}
		if(nearest.size() < 2)
			return new Point[0];
		for (int i = 0; i < nearest.size(); i++) {
			for (int j = i+1; j < nearest.size() ; j++) {
				if (distance(nearest.get(i),nearest.get(j)) < min_diff) {
					ret_points[0] = nearest.get(i);
					ret_points[1] = nearest.get(j);
					min_diff = distance(points.get(i),points.get(j));
				}
			}
		}
		return ret_points;
	}

	@Override
	public Point[] nearestPair() {
		// TODO Auto-generated method stub
		Point[] ret_points = new Point[2];
		if (points.size() == 2){
			ret_points[0] = points.get(0);
			ret_points[1] = points.get(1);
			return ret_points;
		}
		else if(points.size() < 2)
			return ret_points;

		double min_diff = Integer.MAX_VALUE;
		for (int i = 0; i < points.size(); i++) {
			for (int j = i+1; j < points.size() ; j++) {
				if (distance(points.get(i),points.get(j)) < min_diff) {
					ret_points[0] = points.get(i);
					ret_points[1] = points.get(j);
					min_diff = distance(points.get(i),points.get(j));
				}
			}
		}
		return ret_points;
	}


	//TODO: add members, methods, etc.

	private Point[] my_sort(ArrayList<Point> pointArrayList, Boolean axis) {
		int n = pointArrayList.size();
		int[] xis = new int[n];
		int[] yis = new int[n];
		for (int i = 0; i < n; i++) {
			xis[i] = pointArrayList.get(i).getX();
			yis[i] = pointArrayList.get(i).getY();
		}
		Point[] ret_points = new Point[n];
		Boolean found = false;
		if (axis) {
			Arrays.sort(xis);
			for (int i = 0; i < n; i++) {
				found = false;
				for (int j = 0; j < n && !found; j++) {
					if (xis[i] == pointArrayList.get(j).getX()) {
						ret_points[i] = pointArrayList.get(j);
						found = true;
					}
				}
			}
		} else {
			Arrays.sort(yis);
			for (int i = 0; i < n; i++) {
				found = false;
				for (int j = 0; j < n && !found; j++) {
					if (yis[i] == pointArrayList.get(j).getY()) {
						ret_points[i] = pointArrayList.get(j);
						found = true;
					}
				}
			}
		}

		return ret_points;
	}

	private static double distance(Point p1, Point p2)
	{
		return Math.sqrt( Math.pow(p1.getX()-p2.getX(),2)    +Math.pow(p1.getY()-p2.getY(),2)    );
	}

}


