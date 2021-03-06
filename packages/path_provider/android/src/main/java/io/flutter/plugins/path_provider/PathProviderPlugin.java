// Copyright 2017 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.path_provider;

import android.app.Activity;
import android.os.Environment;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.util.PathUtils;

public class PathProviderPlugin implements MethodCallHandler {
  private final Activity activity;

  public static void registerWith(Registrar registrar) {
    MethodChannel channel =
        new MethodChannel(registrar.messenger(), "plugins.flutter.io/path_provider");
    PathProviderPlugin instance = new PathProviderPlugin(registrar.activity());
    channel.setMethodCallHandler(instance);
  }

  private PathProviderPlugin(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    switch (call.method) {
      case "getTemporaryDirectory":
        result.success(getPathProviderTemporaryDirectory());
        break;
      case "getApplicationDocumentsDirectory":
        result.success(getPathProviderApplicationDocumentsDirectory());
        break;
      case "getStorageDirectory":
        result.success(getPathProviderStorageDirectory());
        break;
      default:
        result.notImplemented();
    }
  }

  private String getPathProviderTemporaryDirectory() {
    return activity.getCacheDir().getPath();
  }

  private String getPathProviderApplicationDocumentsDirectory() {
    return PathUtils.getDataDirectory(activity);
  }

  private String getPathProviderStorageDirectory() {
    return Environment.getExternalStorageDirectory().getAbsolutePath();
  }
}
