package ru.cod331n.id;

import ru.cod331n.db.dao.IdDao;

public class Id {

    private static final IdDao idDao = new IdDao().createTable();
    private static long idCounter = idDao.getCurrentId().get();

    public static long nextId() {
        long newId = idCounter++;
        idDao.updateCurrentId(newId);
        return newId;
    }

}
