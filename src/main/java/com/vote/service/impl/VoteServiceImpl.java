package com.vote.service.impl;

import com.vote.model.Vote;
import com.vote.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin( origins = {"http://localhost:3333", "http://localhost:4200"} )
public class VoteServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.vote.service.impl.VoteServiceImpl.class);

    @Autowired
    private VoteRepository voteRepository;

    @org.springframework.transaction.annotation.Transactional
    @GetMapping(path = "/isConnect")
    public String isConnect() {
        return "connection is successful";
    }

    @GetMapping(path = "/getVotes", produces = "application/json")
    public List<String> getVotes() {
        LOGGER.info("Start--> VoteServiceImpl.getVotes()");
        String voter = getVoter();
        LOGGER.info("voter--> VoteServiceImpl.getVotes()"+voter);
//        if(!validateVoter(voter))
//            return null;
        List<Vote> votes = voteRepository.findAllByVoterAndIsDeleted(voter, false);
        List<String> voteString = new ArrayList<>();
        for (Vote vote : votes) {
            voteString.add(vote.getSelection());
        }
        LOGGER.info("End--> VoteServiceImpl.getVotes()");
        return voteString;
    }

    @Transactional
    @PostMapping(path = "/api/createVotes", consumes = "application/json")
    public void createVotes(@RequestBody List<String> voteNames) {
        LOGGER.info("Start--> VoteServiceImpl.createVote()");
        String voter = getVoter();
        LOGGER.info("voter--> VoteServiceImpl.createVote()"+ voter);
//        if(voteNames.size() == 0 || !validateVoter(voter))
//            return;
        deleteVotesIfExists(voter, voteNames.get(0).length());
        addVotes(voteNames, voter);
        LOGGER.info("End--> VoteServiceImpl.createVote()");
    }


    @GetMapping(path = "/api/getVotesCountGroupBySelection", produces = "application/json")
    public List<?> getVotesCountGroupBySelection() {
        LOGGER.info("Start--> VoteServiceImpl.getNamesGroupByName()");
        String voter = getVoter();
        LOGGER.info("ip--> VoteServiceImpl.getNamesGroupByName()"+voter);
        if(!voter.contains("192.168.151.87"))
            return null;
        return voteRepository.getVotesCountGroupBySelection();
    }

    private String getVoter() {
        LOGGER.info("Start--> VoteServiceImpl.getVoter()");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        if (request == null)
            return "";


        LOGGER.info("End--> VoteServiceImpl.getVoter()");
        return request.getRemoteAddr();
    }

    private void addVotes(@RequestBody List<String> voteNames, String voter) {
        LOGGER.info("Start--> VoteServiceImpl.addVotes()" + voter);
        for (String innerName : voteNames) {
            voteRepository.save(new Vote(voter, new Date(), innerName));
        }
        LOGGER.info("End--> VoteServiceImpl.addVotes()");
    }

    private void deleteVotesIfExists(String selection, Integer length) {
        voteRepository.updateIsDeleted(selection, length);
    }

    private boolean validateVoter(String ip) {
        List<String> validIPS = new ArrayList<String>(
                Arrays.asList("192.168.153.8", "192.168.151.171",
                        "192.168.153.8", "192.168.151.142", "192.168.155.39", "192.168.155.33", "192.168.119.4",
                        "192.168.119.16", "192.168.151.67", "192.168.151.91", "192.168.151.96",
                        "192.168.151.205", "192.168.151.200", "192.168.119.8", "192.168.151.196",
                        "192.168.151.76", "192.168.152.7", "192.168.155.13", "192.168.155.5",
                        "192.168.152.197", "192.168.151.81", "192.168.151.77", "192.168.151.92",
                        "192.168.119.56", "192.168.119.18", "192.168.153.55", "192.168.151.210",
                        "192.168.153.34", "192.168.151.95", "192.168.151.85", "192.168.119.52",
                        "192.168.119.54", "192.168.151.197", "192.168.151.225", "192.168.151.87",
                        "192.168.152.43", "192.168.152.35", "192.168.152.27", "192.168.151.65",
                        "192.168.151.78", "192.168.151.68"
                ));
        return validIPS.contains(ip);

    }
}
