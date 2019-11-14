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
package io.knotx.fragments.task.factory;

import io.knotx.fragments.engine.graph.Node;
import io.knotx.fragments.engine.graph.SingleNode;
import io.knotx.fragments.handler.api.Action;
import io.knotx.fragments.handler.api.ActionFactory;
import io.knotx.fragments.handler.api.domain.FragmentContext;
import io.knotx.fragments.handler.api.domain.FragmentResult;
import io.knotx.fragments.task.exception.GraphConfigurationException;
import io.knotx.fragments.task.options.ActionNodeConfigOptions;
import io.knotx.fragments.task.options.GraphNodeOptions;
import io.reactivex.Single;
import io.vertx.reactivex.core.Vertx;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.function.Supplier;

public class ActionNodeFactory implements NodeFactory {

  private ActionProvider actionProvider;

  public ActionNodeFactory() {
    actionProvider = new ActionProvider(supplyFactories());
  }

  @Override
  public String getName() {
    return "action";
  }

  @Override
  public Node newInstance(GraphNodeOptions nodeOptions, Map<String, Node> edges, String taskName,
      TaskFactoryOptions taskOptions, NodeProvider nodeProvider, Vertx vertx) {
    ActionNodeConfigOptions config = new ActionNodeConfigOptions(nodeOptions.getNode().getConfig());
    Action action = actionProvider.get(config.getAction(), taskOptions.getActions(), vertx).orElseThrow(
        () -> new GraphConfigurationException("No provider for action " + config.getAction()));
    return new SingleNode(config.getAction(), toRxFunction(action), edges);
  }

  private Function<FragmentContext, Single<FragmentResult>> toRxFunction(
      Action action) {
    io.knotx.fragments.handler.reactivex.api.Action rxAction = io.knotx.fragments.handler.reactivex.api.Action
        .newInstance(action);
    return rxAction::rxApply;
  }

  private Supplier<Iterator<ActionFactory>> supplyFactories() {
    return () -> {
      ServiceLoader<ActionFactory> factories = ServiceLoader
          .load(ActionFactory.class);
      return factories.iterator();
    };
  }
}
