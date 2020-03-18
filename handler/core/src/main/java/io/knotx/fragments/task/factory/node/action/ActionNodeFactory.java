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
package io.knotx.fragments.task.factory.node.action;

import static io.knotx.fragments.handler.api.metadata.NodeMetadata.single;

import io.knotx.fragments.action.ActionFactoryOptions;
import io.knotx.fragments.action.ActionProvider;
import io.knotx.fragments.action.api.Action;
import io.knotx.fragments.api.FragmentContext;
import io.knotx.fragments.api.FragmentResult;
import io.knotx.fragments.engine.api.node.Node;
import io.knotx.fragments.engine.api.node.single.SingleNode;
import io.knotx.fragments.handler.api.metadata.NodeMetadata;
import io.knotx.fragments.handler.api.metadata.OperationMetadata;
import io.knotx.fragments.task.factory.GraphNodeOptions;
import io.knotx.fragments.task.factory.NodeProvider;
import io.knotx.fragments.task.factory.node.NodeFactory;
import io.knotx.fragments.task.factory.node.NodeOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ActionNodeFactory implements NodeFactory {

  public static final String NAME = "action";
  public static final String METADATA_ALIAS = "alias";
  public static final String METADATA_ACTION_FACTORY = "actionFactory";
  public static final String METADATA_ACTION_CONFIG = "actionConfig";
  private ActionProvider actionProvider;
  private Map<String, ActionFactoryOptions> actionNameToOptions;

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public ActionNodeFactory configure(JsonObject config, Vertx vertx) {
    this.actionNameToOptions = new ActionNodeFactoryConfig(config).getActions();
    actionProvider = new ActionProvider(actionNameToOptions, vertx);
    return this;
  }

  @Override
  public Node initNode(GraphNodeOptions nodeOptions, Map<String, Node> edges,
      NodeProvider nodeProvider) {
    // The implementation is for backwards compatibility of NodeFactory interface
    return initNode(nodeOptions.getNode(), edges, nodeProvider, new HashMap<>());
  }

  @Override
  public Node initNode(NodeOptions nodeOptions, Map<String, Node> edges, NodeProvider nodeProvider,
      Map<String, NodeMetadata> nodesMetadata) {
    ActionNodeConfig config = new ActionNodeConfig(nodeOptions.getConfig());
    Action action = actionProvider.get(config.getAction()).orElseThrow(
        () -> new ActionNotFoundException(config.getAction()));
    final String actionNodeId = UUID.randomUUID().toString();
    nodesMetadata.put(actionNodeId, createActionNodeMetadata(actionNodeId, edges, config));
    return new SingleNode() {
      @Override
      public String getId() {
        return actionNodeId;
      }

      @Override
      public Optional<Node> next(String transition) {
        return Optional.ofNullable(edges.get(transition));
      }

      @Override
      public void apply(FragmentContext fragmentContext, Handler<AsyncResult<FragmentResult>> handler) {
        action.apply(fragmentContext, handler);
      }
    };
  }

  private NodeMetadata createActionNodeMetadata(String actionNodeId, Map<String, Node> edges,
      ActionNodeConfig config) {
    Map<String, String> transitionMetadata = createTransitionMetadata(edges);
    return single(actionNodeId, config.getAction(), transitionMetadata, createOperation(config));
  }

  private Map<String, String> createTransitionMetadata(Map<String, Node> edges) {
    Map<String, String> transitionMetadata = new HashMap<>();
    edges.forEach((transition, node) -> transitionMetadata.put(transition, node.getId()));
    return transitionMetadata;
  }

  private OperationMetadata createOperation(ActionNodeConfig config) {
    ActionFactoryOptions actionConfig = actionNameToOptions.get(config.getAction());
    return new OperationMetadata(NAME, new JsonObject()
        .put(METADATA_ALIAS, config.getAction())
        .put(METADATA_ACTION_FACTORY, actionConfig.getFactory())
        .put(METADATA_ACTION_CONFIG, actionConfig.getConfig()));
  }
}
