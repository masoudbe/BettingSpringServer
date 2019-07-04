package com.vote.service.impl;

import com.vote.model.Vote;
import com.vote.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class VoteServiceImpl {

    @Autowired
    private VoteRepository voteRepository;

    private String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        if (request == null)
            return "";

        return request.getRemoteAddr();
    }




    @Transactional
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/api/createVote", consumes = "application/json")
    public void createVote(@RequestBody List<String> voteNames) {

        String ip = getIpAddress();

        deleteVotesIfExists(ip);

        addVotes(voteNames, ip);
    }


    @GetMapping(path = "/api/getVotes", produces = "application/json")
    public List<String> getVotes() {

        String ip = getIpAddress();

        List<Vote> votes = voteRepository.findAllByIpAndIsDeleted(ip,false);
        List<String> voteString = new ArrayList<>();
        for (Vote vote : votes) {
            voteString.add(vote.getName());
        }
        return voteString;
    }

    @GetMapping(path = "/api/getNamesGroupByName", produces = "application/json")
    public List<?> getNamesGroupByName() {
       return voteRepository.getNamesGroupByName();
    }



    private void addVotes(@RequestBody List<String> voteNames, String ip) {
        for (String innerName : voteNames) {
            voteRepository.save(new Vote(ip, new Date(), innerName));
        }
    }

    private void deleteVotesIfExists(String ip) {
            voteRepository.updateIsDeleted(ip);
    }

    @org.springframework.transaction.annotation.Transactional
    @GetMapping(path = "/isConnect")
    public String isConnect() {
        return "connection is successful";
    }


}
