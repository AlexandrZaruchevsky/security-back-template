package ru.az.secu.cl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.az.secu.services.UtilService;

@Component
@Order(1)
public class InitAdmin implements CommandLineRunner {

    private final UtilService utilService;

    public InitAdmin(UtilService utilService) {
        this.utilService = utilService;
    }

    @Override
    public void run(String... args) throws Exception {
        utilService.addAdmin();
    }

}
