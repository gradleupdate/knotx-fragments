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
package io.knotx.fragments.task.provider;

import io.knotx.fragments.handler.action.ActionOptions;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import java.util.Map;
import java.util.Objects;

@DataObject(generateConverter = true)
public class LocalTaskProviderFactoryOptions {

  public static final String NODE_LOG_LEVEL_KEY = "logLevel";

  private Map<String, ActionOptions> actions;
  private String logLevel;

  public LocalTaskProviderFactoryOptions(JsonObject json) {
    LocalTaskProviderFactoryOptionsConverter.fromJson(json, this);
  }

  public JsonObject toJson() {
    JsonObject jsonObject = new JsonObject();
    LocalTaskProviderFactoryOptionsConverter.toJson(this, jsonObject);
    return jsonObject;
  }

  public Map<String, ActionOptions> getActions() {
    return actions;
  }

  public void setActions(
      Map<String, ActionOptions> actions) {
    this.actions = actions;
  }

  public String getLogLevel() {
    return logLevel;
  }

  public void setLogLevel(String logLevel) {
    this.logLevel = logLevel;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocalTaskProviderFactoryOptions that = (LocalTaskProviderFactoryOptions) o;
    return Objects.equals(actions, that.actions) &&
        Objects.equals(logLevel, that.logLevel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actions, logLevel);
  }

  @Override
  public String toString() {
    return "ConfigurableTaskProviderOptions{" +
        "actions=" + actions +
        ", logLevel='" + logLevel + '\'' +
        '}';
  }
}