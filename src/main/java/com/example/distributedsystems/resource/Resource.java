package com.example.distributedsystems.resource;

import com.example.distributedsystems.service.Service;

import javax.ws.rs.*;
import java.util.ArrayList;

@Path("/Diabetes")
public class Resource {
    private Service mainService  = new Service();
    @Path("/GetStats")
    @GET
    @Produces("text/plain")
    public String getStats (@QueryParam("dateS") String dateS, @QueryParam("dateF") String dateF, @QueryParam("type") String type){

        return mainService.read(dateS,dateF,type);
    }
    @Path("/Insert")
    @POST
    public String insertPatient (String diabeticData){
        String[] mdata= diabeticData.split("&");
        ArrayList<String> mdataL= new ArrayList<String>();
        for(int i=0; i< mdata.length; i++){
            String[] mdata2 = mdata[i].split("=");
            mdataL.add(mdata2[1]);
        }
        return mainService.insert(mdataL.get(0),mdataL.get(1),mdataL.get(2),mdataL.get(3));
    }
    @Path("/Delete")
    @DELETE
    @Produces("text/plain")
    public String deletePatient (String recordId){
        String[] mdata = recordId.split("=");
        return mainService.delete(mdata[1]);
    }
    @Path("/Update")
    @PUT
    @Produces("text/plain")
    public String updatePatient (String params){
        String[] mdata= params.split("&");
        ArrayList<String> mdataL= new ArrayList<String>();
        for(int i=0; i< mdata.length; i++){
            String[] mdata2 = mdata[i].split("=");
            mdataL.add(mdata2[1]);
        }
        return mainService.update(mdataL.get(0),mdataL.get(1),mdataL.get(2),mdataL.get(3));
    }
}
