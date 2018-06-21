

public class NeuralNet {

	public static void main(String[] args) {
		//Step 1, picture and edge detection
		Edge pic = new Edge("2.png");
		pic.edgeDetectorHorizontal("BW");
		pic.edgeDetectorVertical("BW");
		//pic.show();
		//pic.picEdgeH.show();
		//pic.picEdgeV.show();
		//pic.show();
		//pic.picEdgeH.show();
		//pic.picEdgeV.show();
		pic.combineEdgePics();
		pic.show();
		
		//each rgb is a vector. Takes a picture and each rgb becomes a vector. 
		//Vector[][] vectArr = pic.assignVectorArray();
		
		//Then the matrix weights are then multiplied with it to get a 1 or 0.
	}
}
