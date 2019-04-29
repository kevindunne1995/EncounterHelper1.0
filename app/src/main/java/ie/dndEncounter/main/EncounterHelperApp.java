package ie.dndEncounter.main;

import ie.dndEncounter.models.Creature;
import java.util.ArrayList;
import java.util.List;
import android.app.Application;
import android.util.Log;

public class EncounterHelperApp extends Application
{
    public List <Creature> creatureList = new ArrayList<>();

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Encounter Helper", "Let the Encounters Begin");
    }
}