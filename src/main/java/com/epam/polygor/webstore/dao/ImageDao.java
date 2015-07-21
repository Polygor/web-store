package com.epam.polygor.webstore.dao;


import com.epam.polygor.webstore.model.Image;

import java.util.List;

public interface ImageDao extends Dao<Image>{
    List<Image> getImageList() throws DaoException;

    Image findByImagename(String imagename) throws DaoException;
}

