package org.app;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import processing.Status;
import processing.StatusListener;

public class MyStatusListener implements StatusListener {
    private final DefaultListModel<String> listModel;
    private final JList<String> list;

    public MyStatusListener(JList<String> list) {
        this.list = list;
        this.listModel = new DefaultListModel<>();
        this.list.setModel(listModel);
    }

    @Override
    public void statusChanged(Status s) {
        int taskId = s.getTaskId();
        String statusText = "TaskId: " + taskId + " Status: " + s.getProgress() + "%";

        for (int i = 0; i < listModel.getSize(); i++) {
            String text = listModel.get(i);
            if (text.startsWith("TaskId: " + taskId)) {
                listModel.set(i, statusText);
                list.ensureIndexIsVisible(i);
                return;
            }
        }

        listModel.addElement(statusText);
        list.ensureIndexIsVisible(listModel.getSize() - 1);
    }

}

