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
package io.knotx.fragments.engine;

import io.knotx.fragments.engine.graph.Node;
import java.util.Optional;

public class Task {

  private final String name;
  private final Node rootNode;

  public Task(String name) {
    this(name, null);
  }

  public Task(String name, Node rootNode) {
    this.name = name;
    this.rootNode = rootNode;
  }

  public Optional<Node> getRootNode() {
    return Optional.ofNullable(rootNode);
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Task{" +
        "name='" + name + '\'' +
        ", rootNode=" + rootNode +
        '}';
  }
}
