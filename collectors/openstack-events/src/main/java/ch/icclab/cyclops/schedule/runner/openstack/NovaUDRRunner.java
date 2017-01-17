/*
 * Copyright (c) 2015. Zuercher Hochschule fuer Angewandte Wissenschaften
 *  All Rights Reserved.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License"); you may
 *     not use this file except in compliance with the License. You may obtain
 *     a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *     WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *     License for the specific language governing permissions and limitations
 *     under the License.
 */
package ch.icclab.cyclops.schedule.runner.openstack;

import ch.icclab.cyclops.consume.data.mapping.openstack.OpenstackEvent;
import ch.icclab.cyclops.consume.data.mapping.openstack.events.OpenstackNovaEvent;
import ch.icclab.cyclops.consume.data.mapping.usage.OpenStackImageActiveUsage;
import ch.icclab.cyclops.consume.data.mapping.usage.OpenStackUpTimeUsage;
import ch.icclab.cyclops.consume.data.mapping.usage.OpenStackUsage;
import ch.icclab.cyclops.load.Loader;
import ch.icclab.cyclops.load.model.OpenstackSettings;
import ch.icclab.cyclops.persistence.HibernateClient;
import ch.icclab.cyclops.persistence.pulls.LatestPullNova;
import ch.icclab.cyclops.schedule.runner.OpenStackClient;
import ch.icclab.cyclops.util.loggers.SchedulerLogger;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Oleksii Serhiienko
 * Updated on: 26-Aug-16
 * Description: Runner to generate Nova usage records out of events and send to the queue
 */
public  class NovaUDRRunner extends OpenStackClient {
    @Override
    public String getDbName() {
        return OpenstackNovaEvent.class.getSimpleName();
    }

    @Override
    public Class getUsageFormat(){
        return OpenstackNovaEvent.class;
    }

    @Override
    public ArrayList<OpenStackUsage> generateValue(Long eventTime, OpenstackEvent lastEventInScope){
        OpenstackNovaEvent transformedEvent = (OpenstackNovaEvent) lastEventInScope;
        String type  = transformedEvent.getType();
        String _classCPU = null;
        String _classMemory = null;
        String _classDisk = null;
        OpenstackSettings settings = Loader.getSettings().getOpenstackSettings();

        if (type.equals(settings.getOpenstackCollectorEventRun())){
            _classCPU = "OpenStackCPUActiveUsage";
            _classMemory = "OpenStackMemoryActiveUsage";
            _classDisk = "OpenStackDiskActiveUsage";
        }
        if (type.equals(settings.getOpenstackCollectorEventStop())){
            _classCPU = "OpenStackCPUStoppedUsage";
            _classMemory = "OpenStackMemoryStoppedUsage";
            _classDisk = "OpenStackDiskStoppedUsage";
        }
        if (type.equals(settings.getOpenstackCollectorEventPause())){
            _classCPU = "OpenStackCPUPausedUsage";
            _classMemory = "OpenStackMemoryPausedUsage";
            _classDisk = "OpenStackDiskPausedUsage";
        }
        if (type.equals(settings.getOpenstackCollectorEventSuspend())){
            _classCPU = "OpenStackCPUSuspendedUsage";
            _classMemory = "OpenStackMemorySuspendedUsage";
            _classDisk = "OpenStackDiskSuspendedUsage";
        }

        ArrayList<OpenStackUsage> generatedUsages = new ArrayList<>();
        List<String> meters = Arrays.asList(_classCPU, _classMemory, _classDisk);
        Long eventLastTime = transformedEvent.getTime();
        for (String meter: meters){
            generatedUsages.add(new OpenStackUpTimeUsage(eventLastTime, transformedEvent.getAccount(),
                    transformedEvent.getSource(), transformedEvent.getSource_name(),
                    (double) (eventTime - eventLastTime) /1000, //Seconds instead of milliseconds
                    transformedEvent.getVcpus(), meter));
        }

        String description = "";
        if (transformedEvent.getImage_description()!=null){ description = transformedEvent.getImage_description();}
        generatedUsages.add (new OpenStackImageActiveUsage(eventLastTime, transformedEvent.getAccount(),
                transformedEvent.getImage(), transformedEvent.getSource(),
                description, (double) (eventTime - eventLastTime) /1000 //Seconds instead of milliseconds
                )
        );

        return generatedUsages;

    }

    @Override
    public void updateLatestPull(Long time){
        LatestPullNova pull = (LatestPullNova) hibernateClient.getObject(LatestPullNova.class, 1l);
        if (pull == null) {
            pull = new LatestPullNova(time);
        } else {
            pull.setTimeStamp(time);
        }
        SchedulerLogger.log("The last pull set to "+pull.getTimeStamp().toString());
        hibernateClient.persistObject(pull);
    }

    @Override
    public DateTime getLatestPull(){
        DateTime last;
        LatestPullNova pull = (LatestPullNova) HibernateClient.getInstance().getObject(LatestPullNova.class, 1l);
        if (pull == null) {
            last = new DateTime(0);
        } else {
            last = new DateTime(pull.getTimeStamp());
        }
        return last;
    }
}

