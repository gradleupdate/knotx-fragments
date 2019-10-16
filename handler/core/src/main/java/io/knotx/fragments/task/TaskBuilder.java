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
package io.knotx.fragments.task;

import io.knotx.fragments.engine.FragmentEventContext;
import io.knotx.fragments.engine.FragmentsEngine;
import io.knotx.fragments.engine.Task;

/**
 * Produces {@link Task} based on graph configuration, Fragment data and request.
 */
public interface TaskBuilder {

  /**
   * Produces Task that can be executed by {@link FragmentsEngine}
   *
   * @param config - task configuration
   * @param event - contains Fragment, request
   * @return configured task
   */
  Task get(Configuration config, FragmentEventContext event);

}