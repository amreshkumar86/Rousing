package com.rousing.database;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryOptions;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.View;
import com.couchbase.lite.android.AndroidContext;
import com.rousing.oen.rousing.RousingApplication;
import com.rousing.models.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by amreshkumar on 3/30/18.
 */

public class DBManager {

    private static DBManager managerInstance = null;
    private Manager couchbaseManager = null;
    private Database database = null;
    public static DBManager GetSharedDBManager() {
        if(managerInstance == null) {
            managerInstance = new DBManager();
        }

        return managerInstance;
    }

    private DBManager(){
        try {
            couchbaseManager = new Manager(new AndroidContext(RousingApplication.getContext()),Manager.DEFAULT_OPTIONS);
            try {
                database = couchbaseManager.getDatabase("rousing");
            }
            catch (CouchbaseLiteException ex){
                ex.printStackTrace();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<RousingLight> GetAllLights() {
        QueryEnumerator questions = null;
        try {
            questions = RousingLight.getLightsQuery(database).run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        ArrayList<RousingLight> data = new ArrayList<>();
        for (QueryRow question : questions) {
            Document document = question.getDocument();
            RousingLight model = ModelHelper.modelForDocument(document, RousingLight.class);
            data.add(model);
        }
        return data;
    }

    public ArrayList<Group> GetGroups(){
        try
        {
            Query query = database.createAllDocumentsQuery();
            query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);
            QueryEnumerator result = query.run();
            for (Iterator<QueryRow> it = result; it.hasNext(); ) {
                QueryRow row = it.next();
                System.out.print("asd");
            }

            Document doc = database.getDocument("5c41baae-0172-455e-b4da-f52bc0c1ec1d");
            Map<String,Object> map = doc.getProperties();
            System.out.print("asd");

        }
        catch (Exception ex){

        }
        return new ArrayList<Group>();
    }

    public boolean AddLight(RousingLight light){
        ModelHelper.save(database,light);
        return true;
    }
}

