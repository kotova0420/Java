package com.example.Project_server.shares;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharesServiceImp implements SharesService{

    @Autowired
    private SharesRepo sharesRepo;

    @Override
    public Shares create(Shares share) {
        return sharesRepo.save(share);
    }

    @Override
    public List<Shares> readAll() {
        return sharesRepo.findAll();
    }

    @Override
    public Shares read(long id) {
        return sharesRepo.findById(id).orElse(null);
    }

    @Override
    public boolean update(Shares share, long id) {
        if (read(id) != null) {
            Shares updatedShare = new Shares();

            updatedShare.setId(id);
            updatedShare.setName(share.getName());
            updatedShare.setConditions(share.getConditions());
            updatedShare.setStart_date(share.getStart_date());
            updatedShare.setEnd_date(share.getEnd_date());
            updatedShare.setCoefficient(share.getCoefficient());

            sharesRepo.save(updatedShare);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (read(id) != null) {
            sharesRepo.deleteById(id);
            return true;
        }
        return false;
    }


}
