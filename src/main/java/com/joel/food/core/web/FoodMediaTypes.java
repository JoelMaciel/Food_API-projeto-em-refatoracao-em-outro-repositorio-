package com.joel.food.core.web;

import org.springframework.http.MediaType;

public class FoodMediaTypes {

	public static final String V1_APPLICATION_VALUE =  "application/vnd.food.v1+json";
	
	public static final MediaType V1_APPLICATION_JSON =  MediaType.valueOf(V1_APPLICATION_VALUE);
	
	public static final String V2_APPLICATION_VALUE =  "application/vnd.food.v2+json";
	
	public static final MediaType V2_APPLICATION_JSON =  MediaType.valueOf(V2_APPLICATION_VALUE);
}
