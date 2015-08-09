package com.william.mangoreader.daogen;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.william.mangoreader.daogen.MyLibraryMangaCard;

import com.william.mangoreader.daogen.MyLibraryMangaCardDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig myLibraryMangaCardDaoConfig;

    private final MyLibraryMangaCardDao myLibraryMangaCardDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        myLibraryMangaCardDaoConfig = daoConfigMap.get(MyLibraryMangaCardDao.class).clone();
        myLibraryMangaCardDaoConfig.initIdentityScope(type);

        myLibraryMangaCardDao = new MyLibraryMangaCardDao(myLibraryMangaCardDaoConfig, this);

        registerDao(MyLibraryMangaCard.class, myLibraryMangaCardDao);
    }
    
    public void clear() {
        myLibraryMangaCardDaoConfig.getIdentityScope().clear();
    }

    public MyLibraryMangaCardDao getMyLibraryMangaCardDao() {
        return myLibraryMangaCardDao;
    }

}
