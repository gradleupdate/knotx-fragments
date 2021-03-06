/*
 * Copyright (C) 2019 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.knotx.fragments.handler.consumer;

import io.knotx.server.api.context.ClientRequest;
import java.util.List;

import io.knotx.fragments.engine.FragmentEvent;

/**
 * Fragment event consumer receives {@link FragmentEvent} when {@link io.knotx.fragments.engine.Task}
 * evaluation ends. It can share this information with some external tools or even modify fragment.
 */
public interface FragmentEventsConsumer {

  /**
   * Gets a list of processed and unprocessed fragments.
   *
   * @param clientRequest - client request
   * @param fragmentEvents - all fragment events
   */
  void accept(ClientRequest clientRequest, List<FragmentEvent> fragmentEvents);

}
