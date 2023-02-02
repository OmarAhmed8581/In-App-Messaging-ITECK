package com.itecknologi.iteckapp.utils;

import com.itecknologi.iteckapp.models.AgentCallObject;

public interface CallingInterface {

    void onCallAccepted(AgentCallObject obj);

    void onCallDeclined(AgentCallObject obj);

}
