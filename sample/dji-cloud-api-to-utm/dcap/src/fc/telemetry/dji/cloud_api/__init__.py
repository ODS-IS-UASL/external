# -*- coding: utf-8 -*-
"""
本パッケージに含まれるモジュールは動的ロードされるクラスを含む。
この動的ロードされるクラスは 各機能によって固定の名前で参照されインスタンス化される。
そのため、本パッケージのクラス名は以下の名前で作成すること。

- TelemetryHolder
- TelemetryDistributionHost
- TelemetryManagerClientFactory

    ``__init__.py`` の記述内容
        フライトコントローラの種類によって切り替えるモジュールの内、動的ロードの対象となるクラスを定義する。
        具体的なクラスの定義方法は、処理の中で利用するクラスのimport文を記載する。
        各機能が行う動的ロードは、このimport文が定義された前提で ``__init__.py`` があるパッケージを
        ロードポイントとして指定して実行する。
"""
from .telemetry_holder import TelemetryHolder

