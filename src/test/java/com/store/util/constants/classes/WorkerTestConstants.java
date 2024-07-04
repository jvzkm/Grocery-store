package com.store.util.constants.classes;

import com.store.model.dto.worker.WorkerRequestDto;
import com.store.model.entity.Job;
import com.store.model.entity.Worker;

import static com.store.util.constants.TestConstants.ID_FOUR;
import static com.store.util.constants.TestConstants.ID_ONE;
import static com.store.util.constants.TestConstants.ID_THREE;
import static com.store.util.constants.TestConstants.ID_TWO;
import static com.store.util.constants.classes.StoreTestConstants.store1;

public class WorkerTestConstants {

    public static final int VALID_WORKER = 3;
    public static final int WORKER_2 = 2;

    public static final Job job1 =
            Job.builder()
                    .id(ID_ONE)
                    .name("Manager")
                    .percentage(0.1)
                    .build();


    public static final Job job2 =
            Job.builder()
                    .id(ID_TWO)
                    .name("Cashier")
                    .percentage(0.5)
                    .build();


    public static final Worker worker1 =
            Worker.builder()
                    .id(ID_THREE)
                    .name("Trevor")
                    .surname("Tree")
                    .job(job2)
                    .salary(0)
                    .store(store1)
                    .build();


    public static final Worker worker2 =
            Worker.builder()
                    .id(ID_FOUR)
                    .name("WORKER2")
                    .surname("WORKER2")
                    .job(job1)
                    .salary(1333)
                    .store(store1)
                    .build();

    public static final Worker worker3 =
            Worker.builder()
                    .id(ID_TWO)
                    .name("Luke")
                    .surname("Skywalker")
                    .job(job2)
                    .salary(0)
                    .store(store1)
                    .build();

    public static final WorkerRequestDto requestDto1 =
            WorkerRequestDto.builder()
                    .name("WORKER2")
                    .surname("WORKER2")
                    .jobId(ID_ONE)
                    .salary(1333)
                    .storeId(ID_ONE)
                    .build();
}
