/*
 * Copyright (c) 2016. Zuercher Hochschule fuer Angewandte Wissenschaften
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

package ch.icclab.cyclops.load.model;

/**
 * Author: Oleksii
 * Created: 01/06/16
 * Description: Openstack Settings
 */

public class OpenstackSettings {
    private String OpenstackCollectorEventRun;
    private String OpenstackCollectorEventStop;
    private String OpenstackCollectorEventPause;
    private String OpenstackCollectorEventDelete;
    private String OpenstackCollectorEventSuspend;

    private String OpenstackFirstImport;

    private String OpenstackScheduleTime;

    public void setOpenstackCollectorEventRun(String openstackCollectorEventRun) {
        OpenstackCollectorEventRun = openstackCollectorEventRun;
    }

    public void setOpenstackCollectorEventStop(String openstackCollectorEventStop) {
        OpenstackCollectorEventStop = openstackCollectorEventStop;
    }

    public void setOpenstackCollectorEventPause(String openstackCollectorEventPause) {
        OpenstackCollectorEventPause = openstackCollectorEventPause;
    }

    public void setOpenstackCollectorEventDelete(String openstackCollectorEventDelete) {
        OpenstackCollectorEventDelete = openstackCollectorEventDelete;
    }

    public void setOpenstackCollectorEventSuspend(String openstackCollectorEventSuspend) {
        OpenstackCollectorEventSuspend = openstackCollectorEventSuspend;
    }

    public void setOpenstackFirstImport(String openstackFirstImport) {
        OpenstackFirstImport = openstackFirstImport;

    }

    public void setOpenstackScheduleTime(String openstackScheduleTime) {
        OpenstackScheduleTime = openstackScheduleTime;
    }


    public String getOpenstackCollectorEventRun() {
        return OpenstackCollectorEventRun;
    }
    public String getOpenstackCollectorEventStop() { return OpenstackCollectorEventStop; }
    public String getOpenstackCollectorEventPause() { return OpenstackCollectorEventPause; }
    public String getOpenstackCollectorEventDelete() { return OpenstackCollectorEventDelete; }
    public String getOpenstackCollectorEventSuspend() { return OpenstackCollectorEventSuspend; }

    public String getOpenstackFirstImport() {
        return OpenstackFirstImport;
    }

    public String getOpenstackScheduleTime() {
        return OpenstackScheduleTime;
    }
}
