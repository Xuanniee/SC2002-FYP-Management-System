package Controller;

import java.util.ArrayList;

import Entities.Request;


public class RequestDB {

    private ArrayList<Request> requests;

    public RequestDB() {
        this.requests = new ArrayList<Request>();
    }

    // Show request history
    public void getAllRequests() {
        System.out.println("List of all requests:");
        for (Request request : requests) {
            System.out.println("Type of Request: ");

        }
    }

    // register

    // changetitle

    // deregister

    // transfer
}
