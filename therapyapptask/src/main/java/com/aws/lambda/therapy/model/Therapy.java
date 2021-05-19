package com.aws.lambda.therapy.model;

public class Therapy {

	public int TherapistId;
	public String TherapistName;
	public String TherapyName;
	public String TherapyDescription;
	
	public Therapy() {
		
	}
	public Therapy(int TherepistId, String TherapistName,String TherapyName , String TherapyDescription, int TherapistId) {
		 this.TherapistId = TherapistId;
		 this.TherapistName =TherapistName;
		 this.TherapyName =TherapyName;
		 this.TherapyDescription =TherapyDescription;
	}

}

