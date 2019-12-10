package workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.samples.apps.sunflower.data.AppDatabase;
import com.google.samples.apps.sunflower.data.Plant;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import static com.google.samples.apps.sunflower.utilities.Constants.PLANT_DATA_FILENAME;

public class SeedDatabaseWorker extends Worker {
    public SeedDatabaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            InputStream is = getApplicationContext().getAssets().open(PLANT_DATA_FILENAME);
            JsonReader jr = new JsonReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            final Type plantType = new TypeToken<List<Plant>>() {}.getType();
            final List<Plant> plantList = new Gson().fromJson(jr, plantType);

            final AppDatabase database = AppDatabase.getInstance(getApplicationContext());
            database.plantDao().insertAll(plantList);

            return Result.success();
        } catch (Throwable t) {
            return Result.failure();
        }
    }
}
