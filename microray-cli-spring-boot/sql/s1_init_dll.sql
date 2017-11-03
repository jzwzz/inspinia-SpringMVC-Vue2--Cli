CREATE TABLE [dbo].[workflow] (
  [id]           [BIGINT] IDENTITY (1, 1) PRIMARY KEY NOT NULL,
  [op_type]      [VARCHAR](255)                       NULL,
  [op_user_id]   [VARCHAR](255)                       NULL,
  [op_user_name] [VARCHAR](255)                       NULL,
  [op_date_time] [DATETIME]                           NULL,
  [remark]       [VARCHAR](255)                       NULL,
  [archive_id]   [BIGINT]                             NULL,
)

CREATE TABLE [dbo].[archive] (
  [id]               [BIGINT] IDENTITY (1, 1) PRIMARY KEY NOT NULL,
  [file_name]        [VARCHAR](255)                       NULL,
  [batch_date]       [DATE]                               NULL,
  [create_date_time] [DATETIME]                           NULL,
  [send_date_time]   [DATETIME]                           NULL,
  [creator_id]       [VARCHAR](255)                       NULL,
  [creator_name]     [VARCHAR](255)                       NULL,
  [reviewer_id]      [VARCHAR](255)                       NULL,
  [reviewer_name]    [VARCHAR](255)                       NULL,
  [size]             [BIGINT]                             NULL,
  [status]           [VARCHAR](255)                       NULL,
)

CREATE TABLE [dbo].[archive_detail] (
  [id]                  [BIGINT] IDENTITY (1, 1)  NOT NULL,
  [account_type]        [VARCHAR](255)                       NULL,
  [approval_code]       [VARCHAR](255)                       NULL,
  [approval_timestamp]  [VARCHAR](255)                       NULL,
  [card_number]         [VARCHAR](255)                       NULL,
  [filler_field]        [VARCHAR](255)                       NULL,
  [message_type]        [VARCHAR](255)                       NULL,
  [response_code]       [VARCHAR](255)                       NULL,
  [retailer_id]         [VARCHAR](255)                       NULL,
  [proxy_approval_flag] [VARCHAR](255)                       NULL,
  [sequence_number]     [VARCHAR](255)                       NULL,
  [sys_id]              [VARCHAR](255)                       NULL,
  [transaction_amount]  [VARCHAR](255)                       NULL,
  [transaction_code]    [VARCHAR](255)                       NULL,
  [transaction_type]    [VARCHAR](255)                       NULL,
  [archive_id]          [BIGINT]                             NULL,
)
CREATE CLUSTERED INDEX i1 ON archive_detail(archive_id)

CREATE TYPE ARCHIVE_DETAIL_TYPE AS TABLE
(
  account_type        VARCHAR(255),
  approval_code       VARCHAR(255),
  approval_timestamp  VARCHAR(255),
  card_number         VARCHAR(255),
  filler_field        VARCHAR(255),
  message_type        VARCHAR(255),
  response_code       VARCHAR(255),
  retailer_id         VARCHAR(255),
  proxy_approval_flag VARCHAR(255),
  sequence_number     VARCHAR(255),
  sys_id              VARCHAR(255),
  transaction_amount  VARCHAR(255),
  transaction_code    VARCHAR(255),
  transaction_type    VARCHAR(255),
  archive_id          BIGINT
)
