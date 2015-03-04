package org.oregami.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.junit.*;
import org.oregami.dropwizard.ToDoApplication;
import org.oregami.dropwizard.User;
import org.oregami.entities.Language;
import org.oregami.entities.LanguageDao;
import org.oregami.entities.Task;
import org.oregami.entities.TaskDao;
import org.oregami.test.DatabaseUtils;
import org.oregami.test.PersistenceTest;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by sebastian on 25.08.14.
 */
public class TestTaskService {

    static Injector injector = null;

    private EntityManager entityManager;

    @BeforeClass
    public static void init() {
        JpaPersistModule jpaPersistModule = new JpaPersistModule(ToDoApplication.JPA_UNIT);
        injector = Guice.createInjector(jpaPersistModule);
        injector.getInstance(PersistenceTest.class);
        PersistService persistService = injector.getInstance(PersistService.class);
        persistService.start();
    }

    @AfterClass
    public static void finishClass() {
        DatabaseUtils.clearDatabaseTables();
    }

    @Before
    public void startTx() {
        if (entityManager==null) {
            entityManager = injector.getInstance(EntityManager.class);
        }
        entityManager.getTransaction().begin();

    }

    @After
    public void rollbackTx() {
        entityManager.getTransaction().commit();
    }

    @Test
    public void saveTaskWithoutError() {
        TaskService service = injector.getInstance(TaskService.class);
        TaskDao dao = injector.getInstance(TaskDao.class);

        Task t = new Task("name");
        t.setDescription("this is a description");
        ServiceCallContext context = new ServiceCallContext();
        context.setUser(new User("userId-1"));
        ServiceResult<Task> result = service.createNewTask(t, context);

        /*
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();


        Assert.assertFalse(result.hasErrors());
        Assert.assertEquals(0, result.getErrors().size());
        List<Task> all = dao.findAll();
        Assert.assertEquals(1, all.size());

        Task tLoaded = all.get(0);
        tLoaded.setDescription("updated description");

        service.updateTask(tLoaded);
        */


    }


}
