package com.example.android.usnationalparkguide;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;

import com.sjani.usnationalparkguide.Data.AlertDatabase;
import com.sjani.usnationalparkguide.Data.AlertEntity;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AlertDataSourceTest {

    private static final AlertEntity alert = new AlertEntity("1","Park Closure","test","SOME_PARK");

    private AlertDatabase alertDatabase;

    @Before
    public void initDb() throws Exception {
        alertDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),AlertDatabase.class).build();

    }

    @After
    public void closeDb() throws Exception{
        alertDatabase.close();
    }

    @Test
    public void insertAndGetAlert(){
        alertDatabase.alertDao().save(alert);
        List<AlertEntity> alertEntities = alertDatabase.alertDao().getAllAlerts2();
        assertEquals(alertEntities.get(0).getAlert_id(),alert.getAlert_id());
        assertEquals(alertEntities.get(0).getAlert_name(),alert.getAlert_name());
        assertEquals(alertEntities.get(0).getDescription(),alert.getDescription());
        assertEquals(alertEntities.get(0).getParkCode(),alert.getParkCode());
    }

}
