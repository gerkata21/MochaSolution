package com.android.interviewtask.utils

import android.widget.ImageView
import com.android.interviewtask.rest.WeatherApi
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImagefromUrl(position:Int){
    Picasso.with(context).load(WeatherApi.IMAGE_URL+position).into(this);
}

fun String.getAreaCode():String{
    return this
        .replace("https://api.weather.gov/zones/","")
        .replace("/","-")
        .uppercase()
}

val realdatetimeformat =SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
val displaydatetimeformat =SimpleDateFormat("dd-MMM-yy HH:mm");
fun getDisplayformatTime(datetime:String):String{
    displaydatetimeformat.timeZone=TimeZone.getTimeZone("GMT")
    return displaydatetimeformat.format(realdatetimeformat.parse(datetime))
}