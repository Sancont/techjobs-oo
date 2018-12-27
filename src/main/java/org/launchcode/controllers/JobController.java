package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "")
    public String index(Model model,Integer id) {

        // TODO #1 - get the Job with the given ID and pass it into the view - DONE!

        Job someJob = jobData.findById(id);
        model.addAttribute("jobs", someJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()){
            return "new-job";
        }

        Job newJob = new Job();
        String aName = jobForm.getName();
        newJob.setName(aName);

        int anEmployerId = jobForm.getEmployerId();
        Employer anEmployer = jobData.getEmployers().findById(anEmployerId);
        newJob.setEmployer(anEmployer);

        Location aLocation = new Location("");
        aLocation.setValue(jobForm.getLocation());
        newJob.setLocation(aLocation);

        PositionType aPositionType = new PositionType("");
        aPositionType.setValue(jobForm.getPositionType());
        newJob.setPositionType(aPositionType);

        CoreCompetency aCoreCompetency = new CoreCompetency("");
        aCoreCompetency.setValue(jobForm.getCoreCompetency());
        newJob.setCoreCompetency(aCoreCompetency);

        jobData.add(newJob);

        int someId = newJob.getId();
        model.addAttribute(someId);


        return "redirect:";

    }
}
