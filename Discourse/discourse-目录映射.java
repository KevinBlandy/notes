
/cids
	包含当前运行的Docker容器的容器ID。cids是Docker的“等效”pids。每个容器都有一个像hash这样的独特git。

/containers
	此目录用于各种Discourse容器的容器定义。你负责这个目录，它是空的。

/samples
	可用于引导环境的示例容器定义。您可以将模板从此处复制到容器目录中。

/shared
	共享卷的占位符点与各种Discourse容器。您可以选择在容器外部存储某些持久性信息，在我们的例子中，我们保留各种日志文件并将目录上传到外部。这使您可以轻松地重建容器而不会丢失重要信息。将上传保留在容器之外允许您在多个Web实例之间共享它们。

/templates
	幼崽管理的模板，您可以用来引导您的环境。

/image
	Dockerfiles for Discourse; 有关详细信息，请参阅自述文件。

