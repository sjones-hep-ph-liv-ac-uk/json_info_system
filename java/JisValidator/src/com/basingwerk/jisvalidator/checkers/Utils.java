package com.basingwerk.jisvalidator.checkers;

import java.util.ArrayList;

public class Utils {
	public static String getExtrinsics(ArrayList<String> bigList, ArrayList<String> smallList) {
		// All in small list must exist in big list, else return list of missing
		String extrinsics = "";
		for (String small : smallList) {
			Boolean itExists = false;
			for (String big : bigList) {
				if (small.equals(big)) {
					itExists = true;
				}
			}
			if (itExists != true) {
				extrinsics = extrinsics + small + " ";
			}
		}
		return extrinsics;
	}

}
