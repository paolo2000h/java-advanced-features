package com.example.demo.installation;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/installations")
public class InstallationController {
    @Autowired
    private InstallationService installationService;

    @GetMapping("/{installationId}")
    @ApiOperation(value = "Get an installation by ID")
    public ResponseEntity<Installation> getInstallationById(@PathVariable Long installationId) {
        Installation installation = installationService.getInstallationById(installationId);
        return ResponseEntity.ok(installation);
    }

    @PostMapping("")
    @ApiOperation(value = "Create a new installation")
    public ResponseEntity<Installation> createInstallation(@RequestBody Installation installation) {
        Installation createdInstallation = installationService.saveInstallation(installation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInstallation);
    }

    @DeleteMapping("/{installationId}")
    @ApiOperation(value = "Delete an installation by ID")
    public ResponseEntity<Void> deleteInstallationById(@PathVariable Long installationId) {
        installationService.deleteInstallationById(installationId);
        return ResponseEntity.noContent().build();
    }

}

