import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.CounterGroup;
import org.apache.hadoop.mapreduce.Task;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import requestInfo.MapRequestInfo;
import requestInfo.ReduceRequestInfo;
import requestInfo.RequestInformation;

public class RequestInfoCounter extends Configured implements Tool{
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.printf("Usage: %s needs two arguments, input and output " +
                    "files\n", getClass().getSimpleName());
            return -1;
        }

        Task task = new Task();
        task.setJarByClass(RequestInfoCounter.class);
        task.setTaskName("WordCounter");

        FileInputFormat.addInputPath(task, new Path(args[0])); // Путь для чтения
        FileOutputFormat.setOutputPath(task, new Path(args[1]));

        task.setOutputKeyClass(IntWritable.class); //Ключ для вывода
        task.setOutputValueClass(RequestInformation.class); //Значение для вывода
        task.setOutputFormatClass(TextOutputFormat.class); //Вывод в текстовом формате

        task.setMapperClass(MapRequestInfo.class); //Класс для Map стадии
        task.setReducerClass(ReduceRequestInfo.class); //Класс для Reduce

        task.waitForCompletion(true);

        for (CounterGroup group : task.getCounters()) { //Вывод количества браузеров
            for (Counter counter : group) {
                System.out.println(counter.getName() + "  " + counter.getValue());
            }
        }

        if(task.isSuccessful()) {
            System.out.println("Task successful");
        } else if(!task.isSuccessful()) {
            System.out.println("Task was not successful");
        }

        return returnValue;
    }
}
