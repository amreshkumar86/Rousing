package com.rousing.models;

import com.couchbase.lite.Database;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.View;

import java.util.List;
import java.util.Map;

/**
 * Created by amreshkumar on 3/31/18.
 */

public class RousingLight extends Light {
    //<deviceType>urn:schemas-upnp-org:service:RousingLight:1</deviceType>
    //<friendlyName>Rousing NeoPixel Strip</friendlyName>
    //<presentationURL>index.html</presentationURL>
    //<serialNumber>0000000002</serialNumber>
    //<modelName>Rousing NeoPixel Strip 2018</modelName>
    //<modelNumber>RNP00001</modelNumber>
    //<modelURL/>
    //<manufacturer/>
    //<manufacturerURL/>
    //<UDN>uuid:38323636-4558-4dda-9188-cda0e6056a52</UDN>
    public String deviceType;
    public String friendlyName;
    public String serialNumber;
    public String modelName;
    public String modelNumber;
    public String deviceID;
    public Group group;
    public String deviceIP;
    public String _rev;
    public String _id;
    public boolean isActive = false;
    @Override
    public String toString() {
        return this.modelName + ":" + this.modelNumber + " [" + this.serialNumber + "]";
    }

    public static Query getLightsQuery(Database database) {
        // 1
        View allRousingLightsView = database.getView("allRousingLights");
        if (allRousingLightsView.getMap() == null) {
            // 2
            allRousingLightsView.setMap(new Mapper() {
                @Override
                // 3
                public void map(Map<String, Object> document, Emitter emitter) {
                    emitter.emit(document.get("_id"), null);
                }
            }, "1");
        }
        Query query = allRousingLightsView.createQuery();
        return query;
    }
}
