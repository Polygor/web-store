package com.epam.polygor.webstore.service;
import com.epam.polygor.webstore.dao.DaoManager;
import com.epam.polygor.webstore.dao.ImageDao;
import com.epam.polygor.webstore.model.Image;

import java.util.List;

public class ImageService extends AbstractService {
    public ImageService() {
    }

    public Image getImage(long imageId) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            ImageDao imageDao = daoManager.getImageDao();
            return imageDao.findById(imageId);
        }
    }

    public Image getImageByName (String imagename) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            ImageDao imageDao = daoManager.getImageDao();
            return imageDao.findByImagename(imagename);
        }
    }
    public List<Image> getImages() {
        List<Image> images;
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            ImageDao imageDao = daoManager.getImageDao();
            images = imageDao.getImageList();
            return images;
        }
    }

    public Image addImage(Image image) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            ImageDao imageDao = daoManager.getImageDao();
            daoManager.beginTransaction();
            imageDao.insert(image);
            daoManager.closeTransaction();
        }
        return image;
    }
}
