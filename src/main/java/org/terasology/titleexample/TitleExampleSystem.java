/*
 * Copyright 2018 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.titleexample;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.delay.DelayManager;
import org.terasology.logic.delay.DelayedActionTriggeredEvent;
import org.terasology.logic.players.LocalPlayer;
import org.terasology.logic.title.Title;
import org.terasology.registry.In;

// Because we don't need to use it for the server
@RegisterSystem(RegisterMode.CLIENT)
public class TitleExampleSystem extends BaseComponentSystem {

    // To verify this is our delay to do our process
    private static final String DELAY_ACTION_ID = "TITLE_DELAY_ACTION";

    @In
    private DelayManager delayManager;

    @In
    private LocalPlayer localPlayer;

    // Where the title magic happens
    @In
    private Title title;

    @Override
    public void postBegin() {
        // Schedule an action after 1 second from now
        delayManager.addDelayedAction(localPlayer.getCharacterEntity(), DELAY_ACTION_ID, 1000L);
    }

    @ReceiveEvent
    public void onDelayActionTriggered(DelayedActionTriggeredEvent event, EntityRef entity) {
        // Verify that this is our action to process it
        if (event.getActionId().equals(DELAY_ACTION_ID)) {
            long stay = 5 * 1000L; // keep the title for 5 seconds then it will disappear auto
            title.show("Terasology", "Welcome to terasology world!", stay);
        }
    }

}
