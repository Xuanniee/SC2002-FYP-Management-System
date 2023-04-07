package Controller;

import java.util.ArrayList;
import java.util.Scanner;

import Entities.*;
import enums.ProjectStatus;

public class RequestManager {

    private ProjectDB projectDB = new ProjectDB();
    private RequestChangeTitleDB requestChangeTitleDB = new RequestChangeTitleDB();
    private RequestRegisterDB requestRegisterDB = new RequestRegisterDB();
    private RequestDeregisterDB requestDeregisterDB = new RequestDeregisterDB();

    Scanner sc = new Scanner(System.in);

    public void studentRegister(Student student) {
        System.out.print("Please enter Project ID of the project you want to register for: ");
        int projID = sc.nextInt();
        Project targetProject = projectDB.findProject(projID);
        RequestRegister requestRegister = new RequestRegister(student, targetProject);
        requestRegisterDB.addRequest(requestRegister);
    }

    public void changeTitle(Student student, Project project) {
        System.out.print("Please enter new title: ");
        String newTitle = sc.nextLine();
        RequestChangeTitle requestChangeTitle = new RequestChangeTitle(student, newTitle);
        requestChangeTitleDB.addRequest(requestChangeTitle);
    }

    public void studentDeregister(Student student) {
        System.out.print("Please confirm the Project ID of the project you want to deregister from: ");
        int projID = sc.nextInt();
        Project currentProject = student.getAssignedProject();
        if (currentProject.getProjectID() == projID)
        {
            RequestDeregister requestDeregister = new RequestDeregister(student, currentProject);
            requestDeregisterDB.addRequest(requestDeregister);
        }
        else
        {
            System.out.print("Error - You are not assigned to this project!");
        }
    }



    // For non-request : View available projects
    public void viewAvailableProjects(Student student) {
        ArrayList<Project> allProjects = projectDB.getAvailableProjects();
        ArrayList<Project> deregisteredProjects = student.getDeregisteredProjects();

        System.out.println(" ----- List of Available Projects ----- ");

        for (Project project : allProjects) {
            int found = 0;
            for (Project registered : deregisteredProjects ) {
                if (project.getProjectID() == registered.getProjectID()){
                    found = 1;
                }
            }

            if (found == 0 && project.getProjectStatus() == ProjectStatus.AVAILABLE)
            {
                project.viewProjectDetails();
            }
        }
    }



}
